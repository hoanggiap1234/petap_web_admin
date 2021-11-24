/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.Tracker;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface TrackerRepository extends MongoRepository<Tracker, ObjectId> {
    Page<Tracker> findAllByUserIdAndDeleted(String userId, Boolean deleted, Pageable pageable);

    List<Tracker> findAllByUserIdAndDeleted(String userId, Boolean deleted);

    Tracker findByIdAndDeletedAndUserId(String id, Boolean deleted, String userId);

    Tracker findByImeiAndDeleted(String imei, Boolean deleted);

    Tracker findByImeiAndUserIdAndDeleted(String imei, String userId, Boolean deleted);
}
