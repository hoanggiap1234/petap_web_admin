package io.petapp.api.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class TrackingHistoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    List<TrackerGPSHistoryDTO> trackerGPSHistory;
    InformationTrackerDTO trackers;
    List<InformationPetDTO> pets;

}
