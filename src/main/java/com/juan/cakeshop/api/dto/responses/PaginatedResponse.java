package com.juan.cakeshop.api.dto.responses;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@ToString
public class PaginatedResponse<T> {
    private int currentPage;
    private int pageLength;
    private int nextPage;
    private int totalPages;
    private int totalElements;
    private List<T> data;
}
