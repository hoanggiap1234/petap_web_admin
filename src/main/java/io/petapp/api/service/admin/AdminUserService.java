package io.petapp.api.service.admin;

import io.petapp.api.commons.SortData;
import io.petapp.api.dto.user.UserDTO;
import io.petapp.api.enums.UserStatus;
import io.petapp.api.enums.UserType;
import io.petapp.api.model.user.User;
import io.petapp.api.repository.user.UserRepository;
import io.petapp.api.vm.admin.AdminRegisterAccountVM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    private final MongoTemplate mongoTemplate;

    public Page<UserDTO> getListInformation(String userName, Integer page, Integer size, String search, String[] sort) {

//        User user = userRepository.findByUserNameAndDeletedAndType(userName, false, 1);

        modelMapper = new ModelMapper();

        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
            mapper.skip(UserDTO::setActivated);
            mapper.skip(UserDTO::setDevices);
            mapper.skip(UserDTO::setDeviceTokens);
            mapper.skip(UserDTO::setLastLoginDate);
            mapper.skip(UserDTO::setRoles);
            mapper.skip(UserDTO::setSettings);
        });

        Query query = new Query();

        CriteriaDefinition criteriaDefinition = Criteria.where("deleted").is(false);
//            .andOperator(
//                Criteria.where("user_name").is(null)
//                    .orOperator(Criteria.where("user_name").regex(search,"i"))
//            );

        query.addCriteria(criteriaDefinition);

        mongoTemplate.find(query, User.class);

        int total = mongoTemplate.find(query, User.class).size();

        TypedAggregation<User> aggregationPage = Aggregation.newAggregation(
            User.class,
            new MatchOperation(criteriaDefinition),
            new SortOperation(Sort.by(SortData.getOrderByParamSort(sort))),
            new SkipOperation((long) page * (long) size),
            new LimitOperation(size)
        );

        return new PageImpl<UserDTO>(
            mongoTemplate.aggregate(aggregationPage, User.class, User.class)
                .getMappedResults()
                .stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList()),
            PageRequest.of(page, size, Sort.by(SortData.getOrderByParamSort(sort))),
            total
        );
    }

    public void registerUser(AdminRegisterAccountVM adminRegisterAccountVM){

        if (!StringUtils.isBlank(adminRegisterAccountVM.getDisplayName())) {
            adminRegisterAccountVM.setDisplayName(adminRegisterAccountVM.getDisplayName().trim());
        }

        adminRegisterAccountVM.setFirstName(adminRegisterAccountVM.getFirstName().trim());
        adminRegisterAccountVM.setLastName(adminRegisterAccountVM.getLastName().trim());
        adminRegisterAccountVM.setAddress(adminRegisterAccountVM.getAddress().trim());

        modelMapper = new ModelMapper();
        User user = new User();
//        user.setCreatedBy(registerAccountVM.getPhoneNumber());

        modelMapper.map(adminRegisterAccountVM, user);
        user.setPassword(passwordEncoder.encode(adminRegisterAccountVM.getPassword()));
        user.setActivated(true);
        user.setDeleted(false);
        user.setStatus(UserStatus.VERIFIED.getCode());
        user.setType(UserType.SYSTEM_USER.getCode());

        userRepository.save(user);
    }
}
