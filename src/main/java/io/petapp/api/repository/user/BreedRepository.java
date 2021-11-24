/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.Breed;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.List;

@EnableMongoRepositories
public interface BreedRepository extends MongoRepository<Breed, ObjectId> {
    List<Breed> findAllByIdInAndDeleted(List<String> ids, Boolean deleted);
    Breed findByIdAndDeleted(String id, Boolean deleted);
    Page<Breed> findAllByParentIdAndDeletedOrderByPathOrderAsc(String parentId, Boolean deleted, Pageable pageable);
    List<Breed> findAllByIdInAndParentIdIsNullAndDeleted(List<String> ids, Boolean deleted);
}
