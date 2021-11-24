/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */
package io.petapp.api.service.user;

import io.petapp.api.core.SmsBrandNameService;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.admin.VerificationRequestDTO;
import io.petapp.api.enums.VerificationStatus;
import io.petapp.api.model.admin.VerificationRequest;
import io.petapp.api.repository.user.VerificationRequestRepository;
import io.petapp.api.vm.admin.VerifyTokenVM;
import io.petapp.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.apache.directory.api.ldap.model.constants.LdapSecurityConstants;
import org.apache.directory.api.ldap.model.password.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final SmsBrandNameService smsBrandNameService;

    private final VerificationRequestRepository verificationRequestRepository;

    private ModelMapper modelMapper;

    private final Integer TIMES_GET_OTP = 10;

    private final Integer TIMES_GET_OTP_DEVICE = 3;

    private final Integer DURATION_BETWEEN_OTP = 60;

    private final Integer TIMES_GET_OTP_IP = 100;

    public VerificationRequestDTO requestOtp(Locale locale, String receiver, Integer method, Integer type, String deviceId, String ip, String countryCode) {
        receiver = Helper.formatPhone(receiver, countryCode);

        VerificationRequest verificationRequest = verificationRequestRepository.findFirstByReceiverAndMethodAndTypeOrderByRequestedDateDesc(receiver, method, type);

        if (
            verificationRequest != null &&
                TimeUnit.SECONDS.convert(
                    Instant.now().toEpochMilli()
                        - verificationRequest.getRequestedDate(),
                    TimeUnit.MILLISECONDS
                ) < DURATION_BETWEEN_OTP
        ) {
            throw new BusinessException(ExceptionType.TOO_SHORT_DURATION_BETWEEN_OTP_REQUESTS);
        }

        if (verificationRequestRepository.findAllByReceiverAndRequestedDateGreaterThanEqual(
            receiver,
            Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()).size() > TIMES_GET_OTP
        ) {
            throw new BusinessException(ExceptionType.TOO_MANY_TIMES_GET_OTP);
        }

        if (verificationRequestRepository.findAllByDeviceIdAndRequestedDateGreaterThanEqual(
            deviceId,
            Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()).size() > TIMES_GET_OTP_DEVICE
        ) {
            throw new BusinessException(ExceptionType.TOO_MANY_TIMES_GET_OTP_DEVICE);
        }

        if (verificationRequestRepository.findAllByIpAndRequestedDateGreaterThanEqual(
            ip,
            Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()).size() > TIMES_GET_OTP_IP
        ) {
            throw new BusinessException(ExceptionType.TOO_MANY_TIMES_GET_OTP_IP);
        }

        modelMapper = new ModelMapper();
        String otp = Helper.generateOtp();

        List<VerificationRequest> verificationRequestList = verificationRequestRepository.findAllByReceiverAndMethodAndTypeAndStatus(
            receiver, method, type, VerificationStatus.SEND_NOT_VERIFY.getCode()
        ).stream().peek(vr -> vr.setStatus(VerificationStatus.CANCELED.getCode())).collect(Collectors.toList());

        verificationRequestRepository.saveAll(verificationRequestList);

        verificationRequest = new VerificationRequest();

        verificationRequest.setReceiver(receiver);
        verificationRequest.setMethod(method);
        verificationRequest.setRequestedDate(Instant.now().toEpochMilli());
        verificationRequest.setValidityTime(5);
        verificationRequest.setType(type);
        verificationRequest.setStatus(VerificationStatus.SEND_NOT_VERIFY.getCode());
        verificationRequest.setDeviceId(deviceId);
        verificationRequest.setIp(ip);
        verificationRequest = verificationRequestRepository.save(verificationRequest);
        verificationRequest.setToken(new String(PasswordUtil.createStoragePassword(otp + verificationRequest.getId(), LdapSecurityConstants.HASH_METHOD_SHA512)));
        verificationRequest = verificationRequestRepository.save(verificationRequest);
        VerificationRequestDTO verificationRequestDTO = modelMapper.map(verificationRequest, VerificationRequestDTO.class);
        smsBrandNameService.sendOtp(receiver, otp, locale);
        verificationRequestDTO.setOtp(otp);
        return verificationRequestDTO;
    }

    public Boolean verifyOtp(VerifyTokenVM verifyTokenVM) {
        VerificationRequest verificationRequest = verificationRequestRepository.findById(verifyTokenVM.getId());

        if (verificationRequest != null) {
            if (
                !verificationRequestRepository.findFirstByReceiverAndMethodAndTypeOrderByRequestedDateDesc(verificationRequest.getReceiver(), verificationRequest.getMethod(), verificationRequest.getType()).getId().equals(verificationRequest.getId()) ||
                    verificationRequest.getStatus().equals(VerificationStatus.CANCELED.getCode()) ||
                    !Arrays.equals(PasswordUtil.createStoragePassword(verifyTokenVM.getToken() + verifyTokenVM.getId(), LdapSecurityConstants.HASH_METHOD_SHA512), verificationRequest.getToken().getBytes())
            ) {
                verificationRequest.setStatus(VerificationStatus.VERIFY_FAILED.getCode());
                verificationRequestRepository.save(verificationRequest);
                throw new BusinessException(ExceptionType.INVALID_OTP);
            } else if (verificationRequest.getRequestedDate().compareTo(Instant.now().minus(verificationRequest.getValidityTime(), ChronoUnit.MINUTES).toEpochMilli()) < 0) {
                verificationRequest.setStatus(VerificationStatus.VERIFY_FAILED.getCode());
                verificationRequestRepository.save(verificationRequest);
                throw new BusinessException(ExceptionType.EXPIRED_OTP);
            }
            verificationRequest.setStatus(VerificationStatus.VERIFY_SUCCESS.getCode());
            verificationRequest.setDeviceId(verifyTokenVM.getDeviceId());
            verificationRequestRepository.save(verificationRequest);
            return true;
        }
        throw new BusinessException(ExceptionType.NOT_FOUND_RECEIVER);
    }
}
