package io.petapp.api.repository.user;

import io.petapp.api.model.user.SocialGroup;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface SocialGroupRepository extends MongoRepository<SocialGroup, ObjectId> {

    SocialGroup findByIdAndDeleted(String id, Boolean deleted);
    List<SocialGroup> findAllByCreatedByAndPrivacyTypeAndDeletedAndCreatedDateGreaterThanEqual(String userName, Integer privacyType, Boolean deleted, Long createdDate);


}
