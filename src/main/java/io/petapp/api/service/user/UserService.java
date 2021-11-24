/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationUserDTO;
import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.enums.*;
import io.petapp.api.mapper.user.UserMapper;
import io.petapp.api.model.admin.VerificationRequest;
import io.petapp.api.model.user.Tracker;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.TrackerRepository;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.repository.user.VerificationRequestRepository;
import io.petapp.api.vm.user.CheckPhoneVM;
import io.petapp.api.vm.user.PasswordChangeVM;
import io.petapp.api.vm.user.RegisterAccountVM;
import io.petapp.api.vm.user.UpdateAccountVM;
import io.petapp.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service class for managing users.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationRequestRepository verificationRequestRepository;

    private ModelMapper modelMapper;

    private final TrackerRepository trackerRepository;

    //
//    private final AuthorityRepository authorityRepository;
//
//    private final CacheManager cacheManager;
//
//    public UserService(
//        UserRepository userRepository,
//        PasswordEncoder passwordEncoder,
//        AuthorityRepository authorityRepository,
//        CacheManager cacheManager
//    ) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.authorityRepository = authorityRepository;
//        this.cacheManager = cacheManager;
//    }
//
//    public Optional<User> activateRegistration(String key) {
//        log.debug("Activating user for activation key {}", key);
//        return userRepository
//            .findOneByActivationKey(key)
//            .map(
//                user -> {
//                    // activate given user for the registration key.
//                    user.setActivated(true);
//                    user.setActivationKey(null);
//                    userRepository.save(user);
//                    this.clearUserCaches(user);
//                    log.debug("Activated user: {}", user);
//                    return user;
//                }
//            );
//    }
//
    @Transactional
    public void changePassword(String userName, PasswordChangeVM passwordChangeVM) {
        User user;
        if (userName == null) {
            if (!Helper.validatePhoneNumber(passwordChangeVM.getPhoneNumber())) {
                throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
            }

            user = userRepository.findByPhoneNumberAndDeleted(passwordChangeVM.getPhoneNumber(), false);

            if (user == null) {
                throw new BusinessException(ExceptionType.NOT_FOUND_USER);
            }

            VerificationRequest verificationRequest = verificationRequestRepository.findFirstByReceiverAndMethodAndTypeOrderByRequestedDateDesc(
                passwordChangeVM.getPhoneNumber(),
                VerificationMethod.SMS_OTP.getCode(),
                VerificationType.RESET_PASSWORD_SCREEN.getCode()
            );

            if (verificationRequest == null || !Objects.equals(verificationRequest.getStatus(), VerificationStatus.VERIFY_SUCCESS.getCode())) {
                throw new BusinessException(ExceptionType.UNVERIFIED_PHONE_NUMBER);
            }

            verificationRequest.setStatus(VerificationStatus.ACTION_ACCOMPLISHED.getCode());

            verificationRequestRepository.save(verificationRequest);
        } else {
            if (!Helper.validatePhoneNumber(userName)) {
                throw new BusinessException(ExceptionType.INVALID_PHONE_NUMBER);
            }

            user = userRepository.findByUserNameAndDeleted(userName, false);

            if (user == null) {
                throw new BusinessException(ExceptionType.NOT_FOUND_USER);
            }

            if (StringUtils.isBlank(passwordChangeVM.getCurrentPassword()) ||
                !passwordEncoder.matches(passwordChangeVM.getCurrentPassword(), user.getPassword())
            ) {
                throw new BusinessException(ExceptionType.CURRENT_PASSWORD_INVALID);
            }
        }

        user.setPassword(passwordEncoder.encode(passwordChangeVM.getNewPassword()));
        userRepository.save(user);
    }

    public UserDTO findByPhone(String phoneNumber, String countryCode) {
        if (StringUtils.isEmpty(phoneNumber))
            return null;

        phoneNumber = Helper.formatPhone(phoneNumber, countryCode);
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Transactional
    public void registerUser(RegisterAccountVM registerAccountVM) {

        registerAccountVM.setPhoneNumber(Helper.formatPhone(registerAccountVM.getPhoneNumber(), registerAccountVM.getCountryCode()));

        User user = userRepository.findByPhoneNumber(registerAccountVM.getPhoneNumber());

        if (user != null) {
            throw new BusinessException(ExceptionType.EXISTS_USER);
        }

        VerificationRequest verificationRequest = verificationRequestRepository.findFirstByReceiverAndMethodAndTypeOrderByRequestedDateDesc(
            registerAccountVM.getPhoneNumber(),
            VerificationMethod.SMS_OTP.getCode(),
            VerificationType.REGISTER_SCREEN.getCode()
        );
        if (verificationRequest == null || !Objects.equals(verificationRequest.getStatus(), VerificationStatus.VERIFY_SUCCESS.getCode())) {
            throw new BusinessException(ExceptionType.UNVERIFIED_PHONE_NUMBER);
        }

        verificationRequest.setStatus(VerificationStatus.ACTION_ACCOMPLISHED.getCode());

        verificationRequestRepository.save(verificationRequest);

        modelMapper = new ModelMapper();
        user = new User();

        modelMapper.map(registerAccountVM, user);
        user.setPassword(passwordEncoder.encode(registerAccountVM.getPassword()));
        user.setActivated(true);
        user.setDeleted(false);
        user.setStatus(UserStatus.VERIFIED.getCode());
        user.setType(UserType.NORMAL_USER.getCode());
        user.setUserName(user.getPhoneNumber());
        user.setCreatedBy(registerAccountVM.getPhoneNumber());
        user.setLastModifiedBy(registerAccountVM.getPhoneNumber());

        userRepository.save(user);
    }

    public UserDTO login(String userName, User.Device loggedInDevice, String deviceId) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        modelMapper = new ModelMapper();
        user.setLastLoginDate(Instant.now().toEpochMilli());
        List<User.Device> devices = user.getDevices();
        if (devices == null)
            devices = new ArrayList<>();
        User.Device device;
        int idx = -1;
        if (loggedInDevice != null)
            idx = devices.indexOf(loggedInDevice);
        if (!StringUtils.isEmpty(deviceId)) {
            for (int i = 0, size = devices.size(); i < size; i++) {
                if (deviceId.equals(devices.get(i).getId())) {
                    idx = i;
                    break;
                }
            }
        }
        if (idx >= 0) {
            device = devices.get(idx);
        } else {
            device = new User.Device();
        }
        modelMapper.map(loggedInDevice, device);
        device.setStatus(1);
        if (idx < 0) {
            devices.add(device);
        }
        user.setDevices(devices);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    //
//    private boolean removeNonActivatedUser(User existingUser) {
//        if (existingUser.isActivated()) {
//            return false;
//        }
//        userRepository.delete(existingUser);
//        this.clearUserCaches(existingUser);
//        return true;
//    }
//
//    public User createUser(AdminUserDTO userDTO) {
//        User user = new User();
//        user.setLogin(userDTO.getLogin().toLowerCase());
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        if (userDTO.getEmail() != null) {
//            user.setEmail(userDTO.getEmail().toLowerCase());
//        }
//        user.setImageUrl(userDTO.getImageUrl());
//        if (userDTO.getLangKey() == null) {
//            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
//        } else {
//            user.setLangKey(userDTO.getLangKey());
//        }
//        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
//        user.setPassword(encryptedPassword);
//        user.setResetKey(RandomUtil.generateResetKey());
//        user.setResetDate(Instant.now());
//        user.setActivated(true);
//        if (userDTO.getAuthorities() != null) {
//            Set<Authority> authorities = userDTO
//                .getAuthorities()
//                .stream()
//                .map(authorityRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toSet());
//            user.setAuthorities(authorities);
//        }
//        userRepository.save(user);
//        this.clearUserCaches(user);
//        log.debug("Created Information for User: {}", user);
//        return user;
//    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param updateAccountVM user to update.
     * @return updated user.
     */
    public void updateUser(UpdateAccountVM updateAccountVM, String userName, String idDevice) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        if (!StringUtils.isBlank(updateAccountVM.getAvatar())) {
            updateAccountVM.setAvatar(updateAccountVM.getAvatar().trim());
        }

        if (!StringUtils.isBlank(updateAccountVM.getDisplayName())) {
            updateAccountVM.setDisplayName(updateAccountVM.getDisplayName().trim());
        }

        List<User.Device> devices = user.getDevices();
        if (updateAccountVM.getBioAuth() != null && !ObjectUtils.isEmpty(devices)) {
            for (int i = 0, size = devices.size(); i < size; i++) {
                if (idDevice.equals(devices.get(i).getId())) {
                    devices.get(i).setBioAuth(updateAccountVM.getBioAuth());
                    break;
                }
            }
        }

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().
            setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(updateAccountVM, user);

        userRepository.save(user);
    }

    public Boolean checkActivatedUser(String userName) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        return user.getActivated();
    }

    public InformationUserDTO getInformation(String userName) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);

        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        modelMapper = new ModelMapper();

        InformationUserDTO informationUserDTO = modelMapper.map(user, InformationUserDTO.class);

        List<Tracker> trackers = trackerRepository.findAllByUserIdAndDeleted(user.getId(), false);

        if (trackers != null && trackers.size() > 0) {
            informationUserDTO.setCountDevice(trackers.size());
        }

        return informationUserDTO;
    }
//
//    /**
//     * Update basic information (first name, last name, email, language) for the current user.
//     *
//     * @param firstName first name of user.
//     * @param lastName  last name of user.
//     * @param email     email id of user.
//     * @param langKey   language key.
//     * @param imageUrl  image URL of user.
//     */
//    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
//        SecurityUtils
//            .getCurrentUserLogin()
//            .flatMap(userRepository::findOneByLogin)
//            .ifPresent(
//                user -> {
//                    user.setFirstName(firstName);
//                    user.setLastName(lastName);
//                    if (email != null) {
//                        user.setEmail(email.toLowerCase());
//                    }
//                    user.setLangKey(langKey);
//                    user.setImageUrl(imageUrl);
//                    userRepository.save(user);
//                    this.clearUserCaches(user);
//                    log.debug("Changed Information for User: {}", user);
//                }
//            );
//    }
//
//    public void changePassword(String currentClearTextPassword, String newPassword) {
//        SecurityUtils
//            .getCurrentUserLogin()
//            .flatMap(userRepository::findOneByLogin)
//            .ifPresent(
//                user -> {
//                    String currentEncryptedPassword = user.getPassword();
//                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
//                        throw new InvalidPasswordException();
//                    }
//                    String encryptedPassword = passwordEncoder.encode(newPassword);
//                    user.setPassword(encryptedPassword);
//                    userRepository.save(user);
//                    this.clearUserCaches(user);
//                    log.debug("Changed password for User: {}", user);
//                }
//            );
//    }
//
//    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
//        return userRepository.findAll(pageable).map(AdminUserDTO::new);
//    }
//
//    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
//        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
//    }
//
//    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
//        return userRepository.findOneByLogin(login);
//    }
//
//    public Optional<User> getUserWithAuthorities() {
//        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
//    }
//
//    /**
//     * Not activated users should be automatically deleted after 3 days.
//     * <p>
//     * This is scheduled to get fired everyday, at 01:00 (am).
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void removeNotActivatedUsers() {
//        userRepository
//            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
//            .forEach(
//                user -> {
//                    log.debug("Deleting not activated user {}", user.getLogin());
//                    userRepository.delete(user);
//                    this.clearUserCaches(user);
//                }
//            );
//    }
//
//    /**
//     * Gets a list of all the authorities.
//     *
//     * @return a list of all the authorities.
//     */
//    public List<String> getAuthorities() {
//        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
//    }
//
//    private void clearUserCaches(User user) {
//        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
//        if (user.getEmail() != null) {
//            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
//        }
//    }
}
