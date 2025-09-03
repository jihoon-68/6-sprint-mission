package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.file.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class JavaApplication {
    static User setupUser(UserService userService) { // 셋업의 기능만을 사용한다고 하면, User user = userService.crea... 초기화 선언 이후 return 하는 이유가 궁금합니다.
        return userService.create("woody", "woody@codeit.com", "woody1234");
    }

    static Channel setupChannel(ChannelService channelService, Channel.ChannelType type) {
        return channelService.create("공지", "공지 채널입니다.", type);
    }

    static void messageCreateTest(MessageService messageService, User author, Channel channel, Message.MessageType type) {
        Message message = messageService.create("안녕하세요.", author.getId(), channel.getId(), type);
        System.out.println("메시지 생성: " + message.getContent() + "\n"
        + "UUID: " + message.getId() + "\n"
        + "User: " + message.getUser() + "\n"
        + "Channel: " + message.getChannel() + "\n"
        + "Type: " + message.getType() + "\n"
        + "CreateAt: " + message.getCreatedAt() + "\n"
        + "UpdateAt: " + message.getUpdatedAt() + "\n");
    }

    public static void main(String[] args) {
        UserRepository userRepository = new FileUserRepository("src/main/resources/data/user.ser");
        ChannelRepository channelRepository = new FileChannelRepository("src/main/resources/data/channel.ser");
        MessageRepository messageRepository = new FileMessageRepository("src/main/resources/data/message.ser");

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository, channelRepository, userRepository);

        // 셋업
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService, Channel.ChannelType.TEXT);

        // 테스트
        messageCreateTest(messageService, user, channel, Message.MessageType.TEXT); // 임의로 메시지 타입 만듦
    }
}
