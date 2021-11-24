/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the {@link User} entity.
 */
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    User findByPhoneNumberAndDeleted(String phoneNumber, Boolean deleted);

    User findByPhoneNumber(String phoneNumber);

    User findByUserNameAndDeleted(String userName, Boolean deleted);

    User findByUserNameAndDeletedAndType(String userName, Boolean deleted, Integer type);

    Page<User> findByDeletedAndUserNameContainingOrFirstNameContainingOrLastNameContaining(Boolean deleted, String search, Pageable pageable);
 //    Optional<User> findOneByActivationKey(String activationKey);

//    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

//    Optional<User> findOneByResetKey(String resetKey);

//    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
//    Optional<User> findOneByEmailIgnoreCase(String email);

//    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
//    Optional<User> findOneByLogin(String login);

//    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
