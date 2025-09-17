package com.sprint.mission.discodeit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discodeit.repository")
public record DiscodeitProperties(RepositoryType type, String fileDirectory) {
    public DiscodeitProperties {
        if (type == null) {
            type = RepositoryType.JCF;
        }
        if (fileDirectory == null) {
            fileDirectory = ".discodeit";
        }
    }

    public enum RepositoryType {
        JCF, FILE
    }
}
