package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileRepositoryConfig {
    private final DiscodeitProperties properties;

    public FileRepositoryConfig(DiscodeitProperties properties) {
        this.properties = properties;
    }

    @Bean
    public UserRepository userRepository() {
        return new FileUserRepository(properties.fileDirectory());
    }

    @Bean
    public ChannelRepository channelRepository() {
        return new FileChannelRepository(properties.fileDirectory());
    }

    @Bean
    public MessageRepository messageRepository() {
        return new FileMessageRepository(properties.fileDirectory());
    }

    @Bean
    public ReadStatusRepository readStatusRepository() {
        return new FileReadStatusRepository(properties.fileDirectory());
    }

    @Bean
    public UserStatusRepository userStatusRepository() {
        return new FileUserStatusRepository(properties.fileDirectory());
    }

    @Bean
    public BinaryContentRepository binaryContentRepository() {
        return new FileBinaryContentRepository(properties.fileDirectory());
    }
}
