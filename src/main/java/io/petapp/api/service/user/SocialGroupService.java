package io.petapp.api.service.user;

import io.petapp.api.core.exception.BusinessException;
import io.petapp.api.core.exception.ExceptionType;
import io.petapp.api.enums.GroupMemberRole;
import io.petapp.api.model.user.*;
import io.petapp.api.repository.user.*;
import io.petapp.api.vm.user.CalendarVM;
import io.petapp.api.vm.user.SocialGroupVM;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocialGroupService {

    private final UserRepository userRepository;

    private final SocialGroupRepository socialGroupRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final Integer PUBLIC_GROUP_PER_USER_DAY = 3;

    private final BreedRepository breedRepository;

    private final CalendarRepository calendarRepository;

    private ModelMapper modelMapper;

    @Transactional
    public void addSocialGroup(SocialGroupVM socialGroupVM, String userName) {

        User user = userRepository.findByUserNameAndDeleted(userName, false);
        if (user == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_USER);
        }

        if (socialGroupRepository.findAllByCreatedByAndPrivacyTypeAndDeletedAndCreatedDateGreaterThanEqual(
            userName,
            socialGroupVM.getPrivacyType(),
            false,
            Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()).size() > PUBLIC_GROUP_PER_USER_DAY) {
            throw new BusinessException(ExceptionType.TOO_MANY_PUBLIC_GROUPS_PER_DAY);
        }

        SocialGroup socialGroup;

        socialGroup = new SocialGroup();

        if (breedRepository.findAllByIdInAndParentIdIsNullAndDeleted(Arrays.stream(socialGroupVM.getBreeds()).collect(Collectors.toList()), false).size() != socialGroupVM.getBreeds().length) {
            throw new BusinessException(ExceptionType.NOT_FOUND_BREED);
        }


        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);

        if (!StringUtils.isBlank(socialGroupVM.getDescription())) {
            socialGroupVM.setDescription(socialGroupVM.getDescription().trim());
        }

        modelMapper.map(socialGroupVM, socialGroup);
        socialGroup.setCreatedBy(userName);
        socialGroup.setDeleted(false);
        socialGroupRepository.save(socialGroup);

        List<Calendar> calendar = calendarRepository.findAllByTypeAndItemIdAndDeleted(1, socialGroup.getId(), false);

        String[] idCalendar;

        GroupMember groupMember = new GroupMember();
        groupMember.setSocialGroupId(socialGroup.getId());
        groupMember.setUserId(user.getId());
        groupMember.setCreatedBy(userName);
        groupMember.setIsOwner(true);
        groupMember.setRole(GroupMemberRole.ADMINISTRATOR.getCode());

        groupMemberRepository.save(groupMember);
    }

    public void addSocialGroupCalendar(CalendarVM calendarVM) {

        SocialGroup socialGroup = socialGroupRepository.findByIdAndDeleted(calendarVM.getItemId(), false);

        if (socialGroup == null) {
            throw new BusinessException(ExceptionType.NOT_FOUND_SOCIAL_GROUP);
        }

    }
}
