package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.repository.jcf.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public UserRepository jcfUserRepository() {
        return new JCFUserRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public ChannelRepository jcfChannelRepository() {
        return new JCFChannelRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public MessageRepository jcfMessageRepository() {
        return new JCFMessageRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public ReadStatusRepository jcfReadStatusRepository() {
        return new JCFReadStatusRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public UserStatusRepository jcfUserStatusRepository() {
        return new JCFUserStatusRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf")
    public BinaryContentRepository jcfBinaryContentRepository() {
        return new JCFBinaryContentRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public UserRepository fileUserRepository() {
        return new FileUserRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public ChannelRepository filechannelRepository() {
        return new FileChannelRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public MessageRepository fileMessageRepository() {
        return new FileMessageRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public ReadStatusRepository fileReadStatusRepository() {
        return new FileReadStatusRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public UserStatusRepository fileUserStatusRepository() {
        return new FileUserStatusRepository();
    }

    @Bean
    @ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
    public BinaryContentRepository fileBinaryContentRepository() {
        return new FileBinaryContentRepository();
    }

}
