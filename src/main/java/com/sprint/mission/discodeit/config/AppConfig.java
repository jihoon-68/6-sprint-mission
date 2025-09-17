package com.sprint.mission.discodeit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.InstantSource;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class AppConfig {

    @Bean
    public Supplier<UUID> idGenerator() {
        return UUID::randomUUID;
    }

    @Bean
    public InstantSource instantSource() {
        return InstantSource.system();
    }
}
