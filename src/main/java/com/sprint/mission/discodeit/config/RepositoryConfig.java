package com.sprint.mission.discodeit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class RepositoryConfig {

    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    private final RepositoryProperties repositoryProperties;

    public RepositoryConfig(RepositoryProperties repositoryProperties) {
        this.repositoryProperties = repositoryProperties;
    }

    @ConfigurationProperties(prefix = "discodeit.repository")
    public record RepositoryProperties(String type, String fileDirectory) {
    }

    @Configuration
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
    @ComponentScan(basePackages = "com.sprint.mission.discodeit.repository.jcf",
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
    public static class JCFRepositoryConfig {
    }

    @Configuration
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    @ComponentScan(basePackages = "com.sprint.mission.discodeit.repository.file",
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
    public static class FileRepositoryConfig {

        @Bean
        public Path basePath(RepositoryProperties repositoryProperties) throws IOException {
            Path basePath = Path.of(repositoryProperties.fileDirectory());
            Files.createDirectories(basePath);
            return basePath;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void on() {
        log.info("{}", repositoryProperties);
    }
}
