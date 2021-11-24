/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface PetRepository extends MongoRepository<Pet, ObjectId> {
    Page<Pet> findAllByUserIdAndDeletedOrderByCreatedDateDesc(String userId, Boolean deleted, Pageable pageable);

    Pet findByIdAndUserIdAndDeleted(String id, String userId, Boolean deleted);

    Pet findByTrackerId(String trackerId);

}
