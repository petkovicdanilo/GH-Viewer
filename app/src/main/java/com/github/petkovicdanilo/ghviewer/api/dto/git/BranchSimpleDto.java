package com.github.petkovicdanilo.ghviewer.api.dto.git;

import lombok.Getter;

@Getter
public class BranchSimpleDto {
    private String name;

    private Commit commit;

    @Getter
    public class Commit {
        private String sha;
    }
}
