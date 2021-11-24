/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.service.user;

import io.petapp.api.core.Constants;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.NotificationDTO;
import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.mapper.user.NotificationMapper;
import io.petapp.api.model.user.Notification;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.NotificationRepository;
import io.petapp.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public Page<NotificationDTO> getAllNotification(String phoneNumber, Integer page, Integer size, String search) {
        User user = userRepository.findByUserNameAndDeleted(phoneNumber, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        return notificationRepository.findAllByUserIdOrderByIdDesc(
            user.getId(),
            PageRequest.of(
                page, size
            )
        ).map(NotificationMapper.INSTANCE::toDTO);
    }

    public NotificationDTO getNotificationById(String userName, String id) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Notification notification = notificationRepository.
            findByIdAndUserId(id, user.getId());
        if (notification == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_NOTIFICATION);
        }
        return NotificationMapper.INSTANCE.toDTO(notification);
    }
}
