/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.admin.VerificationRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface VerificationRequestRepository extends MongoRepository<VerificationRequest, ObjectId> {
    List<VerificationRequest> findAllByReceiverAndMethodAndTypeAndStatus(String receiver, Integer method, Integer type, Integer status);
    VerificationRequest findFirstByReceiverAndMethodAndTypeOrderByRequestedDateDesc(String receiver, Integer method, Integer type);
    VerificationRequest findById(String id);
    List<VerificationRequest> findAllByReceiverAndRequestedDateGreaterThanEqual(String receiver, Long currentTime);
    List<VerificationRequest> findAllByDeviceIdAndRequestedDateGreaterThanEqual(String deviceId, Long currentTime);
    List<VerificationRequest> findAllByIpAndRequestedDateGreaterThanEqual(String ip, Long currentTime);

}
