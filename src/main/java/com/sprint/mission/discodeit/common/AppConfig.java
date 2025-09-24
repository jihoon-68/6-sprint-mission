package com.sprint.mission.discodeit.common;

import com.sprint.mission.discodeit.DiscodeitApplication;
import com.sprint.mission.discodeit.common.persistence.CrudRepository;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.time.InstantSource;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
@ComponentScan(
        basePackageClasses = DiscodeitApplication.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CrudRepository.class)
)
@ConfigurationPropertiesScan(basePackageClasses = DiscodeitApplication.class)
public class AppConfig {

    @Bean
    Supplier<UUID> idGenerator() {
        return UUID::randomUUID;
    }

    @Bean
    InstantSource instantSource() {
        return InstantSource.system();
    }
}
