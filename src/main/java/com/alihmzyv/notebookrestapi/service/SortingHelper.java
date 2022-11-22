package com.alihmzyv.notebookrestapi.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortingHelper {
    public List<Sort.Order> createSortOrder(List<String> sort) {
        if (sort.size() == 2
                && (sort.get(1).equalsIgnoreCase("asc") || sort.get(1).equalsIgnoreCase("desc"))) {
            return List.of(new Sort.Order(Sort.Direction.fromString(sort.get(1)), sort.get(0)));
        }
        return sort.stream()
                .map(sortAndOrder -> sortAndOrder.split(","))
                .map(sortAndOrder -> {
                    if (sortAndOrder.length == 1) {
                        return new Sort.Order(Sort.Direction.ASC, sortAndOrder[0]);
                    }
                    return new Sort.Order(Sort.Direction.fromString(sortAndOrder[1]), sortAndOrder[0]);
                })
                .toList();
    }
}
