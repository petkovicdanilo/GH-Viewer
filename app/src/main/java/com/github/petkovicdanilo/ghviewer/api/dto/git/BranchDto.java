package com.github.petkovicdanilo.ghviewer.api.dto.git;

import lombok.Getter;

@Getter
public class BranchDto {
    private String name;

    private Commit commit;

    @Getter
    public class Commit {
        private String sha;

        private CommitInner commit;

        @Getter
        public class CommitInner {

            private String message;

            private Tree tree;

            @Getter
            public class Tree {
                private String sha;
            }
        }
    }
}
