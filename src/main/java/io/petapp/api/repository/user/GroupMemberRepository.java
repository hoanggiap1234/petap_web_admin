package io.petapp.api.repository.user;

import io.petapp.api.model.user.GroupMember;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface GroupMemberRepository extends MongoRepository<GroupMember, ObjectId> {

}
