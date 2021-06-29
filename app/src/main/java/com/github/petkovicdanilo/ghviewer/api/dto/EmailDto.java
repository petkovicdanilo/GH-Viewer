package com.github.petkovicdanilo.ghviewer.api.dto;

import lombok.Getter;

@Getter()
public class EmailDto {
    private String email;
    private boolean verified;
    private boolean primary;
    private String visibility;
}
