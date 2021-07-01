package com.github.petkovicdanilo.ghviewer.api.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class RepositorySearchResultDto {
    private int totalCount;

    private List<RepositoryDto> items;
}
