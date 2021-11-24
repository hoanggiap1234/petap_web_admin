/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.Notification;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {
    Page<Notification> findAllByUserIdOrderByIdDesc(String userId, Pageable pageable);

    Notification findByIdAndUserId(String id, String userId);
}
