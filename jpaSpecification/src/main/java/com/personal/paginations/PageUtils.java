package com.personal.paginations;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/*
    * pageable = pagination
 */

public interface PageUtils {
    int DEFAULT_PAGE_SIZE = 10;
    int DEFAULT_PAGE_NUMBER = 1;
    String PAGE_SIZE = "_size"; // key for page size
    String PAGE_NUMBER = "_page"; // key for page number

    static Pageable getPageable(int pageNumber, int pageSize) {
        int validPage = Math.max(pageNumber, DEFAULT_PAGE_NUMBER);
        int validSize = Math.max(pageSize, 1);
        return PageRequest.of(validPage - 1, validSize);
    }
}