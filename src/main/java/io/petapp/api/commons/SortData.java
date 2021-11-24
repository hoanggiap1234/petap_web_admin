package io.petapp.api.commons;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortData {
    private static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public static List<Sort.Order> getOrderByParamSort(String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        try {
            //sort
            if (sort.length > 2) {
                // will sort more than 2 columns
                for (int i = 0; i < sort.length; i += 2) {
                    orders.add(new Sort.Order(SortData.getSortDirection(sort[i + 1]), sort[i]));
                }
            } else {
                // sort=[column, direction]
                orders.add(new Sort.Order(SortData.getSortDirection(sort[1]), sort[0]));
            }
        } catch (Exception e) {
            System.out.println("Bug Sort " + e.getMessage());
        }
        return orders;
    }
}
