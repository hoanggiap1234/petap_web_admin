package io.petapp.api.service.user;

import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.dto.user.InformationPetDTO;
import io.petapp.api.dto.user.InformationTrackerDTO;
import io.petapp.api.dto.user.TrackerGPSHistoryDTO;
import io.petapp.api.dto.user.TrackingHistoryDTO;
import io.petapp.api.model.user.Tracker;
import io.petapp.api.model.user.TrackerGPSHistory;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.PetRepository;
import io.petapp.api.repository.user.TrackerGPSHistoryRepository;
import io.petapp.api.repository.user.TrackerRepository;
import io.petapp.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackerGPSHistoryService {

    private final MongoTemplate mongoTemplate;

    private final UserRepository userRepository;

    private final TrackerRepository trackerRepository;

    private final PetRepository petRepository;

    private final TrackerGPSHistoryRepository trackerGPSHistoryRepository;

    ModelMapper modelMapper;

    public TrackingHistoryDTO getListTrackerHistory(String userName, String idTracker, Long fromDate, Long toDate) {
        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        Tracker tracker = trackerRepository.findByIdAndDeletedAndUserId(idTracker, false, user.getId());

        if (tracker == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_TRACKER);
        }

        modelMapper = new ModelMapper();

        TrackingHistoryDTO trackingHistoryDTO = new TrackingHistoryDTO();
        InformationTrackerDTO informationTrackerDTO = new InformationTrackerDTO();

        informationTrackerDTO.setName(tracker.getName());
        informationTrackerDTO.setAvatar(tracker.getAvatar());

        trackingHistoryDTO.setTrackers(informationTrackerDTO);

        Query queryTrackerHistory = new Query();

        queryTrackerHistory.addCriteria(
            Criteria.where("utc_time").gte(fromDate).lte(toDate)
                .and("user_id").is(new ObjectId(user.getId()))
                .and("tracker_id").is(new ObjectId(tracker.getId()))
        );

        trackingHistoryDTO.setTrackerGPSHistory(mongoTemplate.find(
            queryTrackerHistory, TrackerGPSHistoryDTO.class, "tracker_gps_history")
        );

        queryTrackerHistory.fields().include("pet_id");

        Query queryPet = new Query();
        queryPet.addCriteria(
            Criteria.where("deleted").is(false)
                .and("_id").in(mongoTemplate.findDistinct(queryTrackerHistory, "pet_id", TrackerGPSHistory.class, ObjectId.class))
        );

        queryPet.fields().include("_id", "avatar");

        trackingHistoryDTO.setPets(mongoTemplate.find(queryPet, InformationPetDTO.class, "pets"));
        return trackingHistoryDTO;
    }
}
