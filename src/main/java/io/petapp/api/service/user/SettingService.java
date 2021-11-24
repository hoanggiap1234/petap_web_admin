/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.user.UpdateSettingVM;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final UserRepository userRepository;

    private ModelMapper modelMapper;

    public User.Setting getSettings(String userName) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user != null) {
            return user.getSetting();

        }
        throw new BusinessException(ExceptionType.NOT_FOUND_USER);

    }

    public void updateSetting(UpdateSettingVM updateSettingVM, String userName) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);
        modelMapper = new ModelMapper();
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }
        User.Setting setting = user.getSetting() == null ? new User.Setting() : user.getSetting();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(updateSettingVM, setting);
        user.setSetting(setting);
        userRepository.save(user);
    }
}
