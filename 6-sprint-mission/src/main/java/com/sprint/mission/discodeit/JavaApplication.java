package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.channeldto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.messagedto.CreateMessageDto;
import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.file.*;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaApplication {
    static User setupUser(UserService userService) {
        CreateUserDto createUserDto = new CreateUserDto("woody", "woody@codeit.com", "woody1234");
        User user = userService.create(createUserDto);
        return user;
    }

    static Channel setupChannel(ChannelService channelService, List<UUID> userIds) {
        CreateChannelDto createChannelDto = new CreateChannelDto(ChannelType.PUBLIC, "공지", "공지 채널입니다.",userIds);
        Channel channel = channelService.create(createChannelDto);
        return channel;
    }

    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
        List<String> imagePathList = List.of("c://imagePath", "c://imagePath2");
        CreateMessageDto createMessageDto = new CreateMessageDto("안녕하세요.", channel.getId(), author.getId(),imagePathList);
        Message message = messageService.create(createMessageDto);
        System.out.println("메시지 생성: " + message.getId());
    }


    public static void main(String[] args) {
        // 레포지토리 초기화
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();
        BinaryContentRepository binaryContentRepository = new FileBinaryContentRepository();
        UserStatusRepository userStatusRepository = new FileUserStatusRepository();
        ReadStatusRepository readStatusRepository = new FileReadStatusRepository();

        // 서비스 초기화
        UserService userService = new BasicUserService(userRepository,userStatusRepository,binaryContentRepository);
        ChannelService channelService = new BasicChannelService(channelRepository,readStatusRepository,messageRepository,userRepository);
        MessageService messageService = new BasicMessageService(messageRepository, channelRepository, userRepository,binaryContentRepository);

        //셋업
        User user = setupUser(userService);
        List<UUID> userIds = new ArrayList<>();
        userIds.add(user.getId());
        Channel channel = setupChannel(channelService,userIds);
        // 테스트
        messageCreateTest(messageService, channel, user);
    }
}
