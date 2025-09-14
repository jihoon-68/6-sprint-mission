package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileReadStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserStatusRepository;
import com.sprint.mission.discodeit.repository.file.FileBinaryContentRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicReadStatusService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import com.sprint.mission.discodeit.service.basic.BasicBinaryContentService;
import com.sprint.mission.discodeit.service.basic.BasicAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // ==== Repositories ====
    @Bean
    public UserRepository userRepository() {
        return new FileUserRepository();
    }

    @Bean
    public MessageRepository messageRepository() {
        return new FileMessageRepository();
    }

    @Bean
    public ChannelRepository channelRepository() {
        return new FileChannelRepository();
    }

    @Bean
    public ReadStatusRepository readStatusRepository() {
        return new FileReadStatusRepository();
    }

    @Bean
    public UserStatusRepository userStatusRepository() {
        return new FileUserStatusRepository();
    }

    @Bean
    public BinaryContentRepository binaryContentRepository() {
        return new FileBinaryContentRepository();
    }

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
