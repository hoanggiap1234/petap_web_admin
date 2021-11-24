package io.petapp.api.repository.user;

import io.petapp.api.model.user.TrackerGPSHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface TrackerGPSHistoryRepository extends MongoRepository<TrackerGPSHistory, ObjectId> {
}
