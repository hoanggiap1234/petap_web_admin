/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.SafeZone;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface SafeZoneRepository extends MongoRepository<SafeZone, ObjectId> {
    Page<SafeZone> findAllByUserIdAndDeletedOrderByCreatedDateDesc(String userId, Boolean deleted, Pageable pageable);

    SafeZone findByIdAndUserIdAndDeleted(String id, String userId, Boolean deleted);
}
