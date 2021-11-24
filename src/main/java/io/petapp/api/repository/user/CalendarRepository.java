package io.petapp.api.repository.user;

import io.petapp.api.model.user.Calendar;
import io.petapp.api.model.user.GroupMember;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface CalendarRepository extends MongoRepository<Calendar, ObjectId> {
    List<Calendar> findAllByTypeAndItemIdAndDeleted(Integer type, String itemId, Boolean deleted);
}
