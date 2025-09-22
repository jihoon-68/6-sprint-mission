package com.sprint.mission.discodeit.common;

import com.sprint.mission.discodeit.DiscodeitApplication;
import com.sprint.mission.discodeit.common.persistence.CrudRepository;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class RepositoryConfig {

    private static final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    private final RepositoryProperties repositoryProperties;

    RepositoryConfig(RepositoryProperties repositoryProperties) {
        this.repositoryProperties = repositoryProperties;
    }

    @ConfigurationProperties(prefix = "discodeit.repository")
    record RepositoryProperties(String type, String fileDirectory) {
    }

    @Configuration
    @ComponentScan(
            basePackageClasses = DiscodeitApplication.class,
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CrudRepository.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*\\.File.*")
    )
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
    static class JCFRepositoryConfig {
    }

    @Configuration
    @ComponentScan(
            basePackageClasses = DiscodeitApplication.class,
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CrudRepository.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*\\.JCF.*")
    )
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    static class FileRepositoryConfig {

        @Bean
        Path basePath(RepositoryProperties repositoryProperties) throws IOException {
            Path basePath = Path.of(repositoryProperties.fileDirectory());
            Files.createDirectories(basePath);
            return basePath;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    void on() {
        log.info("{}", repositoryProperties);
    }
}
