/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.api.repository.user;

import io.petapp.api.model.admin.OnBoarding;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OnBoardingRepository {

    private final MongoTemplate mongoTemplate;

    public List<OnBoarding> getAllActive() {
        long timestamp = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("activated").exists(true));
        query.addCriteria(Criteria.where("start_time").lt(timestamp));
        query.addCriteria(Criteria.where("end_time").gt(timestamp));
        query.with(Sort.by(Sort.Direction.ASC, "sort_order"));
        return mongoTemplate.find(query, OnBoarding.class);
    }
}
