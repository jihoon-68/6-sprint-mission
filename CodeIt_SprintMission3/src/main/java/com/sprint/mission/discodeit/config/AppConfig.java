package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // ==== Services ====
    @Bean
    public UserService userService(UserRepository userRepository, UserStatusRepository userStatusRepository, BinaryContentRepository binaryContentRepository) {
        return new BasicUserService(userRepository, userStatusRepository, binaryContentRepository);
    }

    @Bean
    public MessageService messageService(MessageRepository messageRepository, ChannelRepository channelRepository, UserRepository userRepository, BinaryContentRepository binaryContentRepository) {
        return new BasicMessageService(messageRepository, channelRepository, userRepository, binaryContentRepository);
    }

    @Bean
    public ChannelService channelService(ChannelRepository channelRepository, MessageRepository messageRepository, ReadStatusRepository readStatusRepository, UserRepository userRepository) {
        return new BasicChannelService(channelRepository, messageRepository, readStatusRepository, userRepository);
    }

    @Bean
    public AuthService authService(UserRepository userRepository, UserStatusRepository userStatusRepository) {
        return new BasicAuthService(userRepository, userStatusRepository);
    }

    @Bean
    public ReadStatusService readStatusService(ReadStatusRepository readStatusRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        return new BasicReadStatusService(readStatusRepository, userRepository, channelRepository);
    }

    @Bean
    public UserStatusService userStatusService(UserStatusRepository userStatusRepository, UserRepository userRepository) {
        return new BasicUserStatusService(userStatusRepository, userRepository);
    }

    @Bean
    public BinaryContentService binaryContentService(BinaryContentRepository binaryContentRepository) {
        return new BasicBinaryContentService(binaryContentRepository);
    }
}
