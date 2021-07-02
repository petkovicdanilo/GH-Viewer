package com.github.petkovicdanilo.ghviewer.api.dto.git;

import lombok.Getter;

@Getter
public class BlobDto {
    private String sha;
    private String content;
}
