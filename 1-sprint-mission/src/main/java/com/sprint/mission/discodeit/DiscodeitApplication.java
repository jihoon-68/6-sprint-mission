package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.DTOs.Channel.PublicChannel;
import com.sprint.mission.discodeit.DTOs.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.DTOs.User.UserInfo;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {
    static User setupUser(UserService userService) {

        UserInfo info = new UserInfo("woody", "woody@codeit.com", "woody1234", null);

        var user = userService.create(info);

//        BinaryContent binaryContent = new BinaryContent(user.getId(), [byte], ".jpg");
        return user;
    }

    static Channel setupChannel(ChannelService channelService) {
        var dto = new PublicChannel("공지", "공지 채널입니다.");
        Channel channel = channelService.create(dto);
        return channel;
    }

    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
        var dto = new MessageCreateRequest("안녕하세요.", channel.getId(), author.getId(), null);

        Message message = messageService.create(dto);
        System.out.println("메시지 생성: " + message.getId());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        ChannelService channelService = context.getBean(ChannelService.class);
        MessageService messageService = context.getBean(MessageService.class);

        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);
        // 테스트
        messageCreateTest(messageService, channel, user);
    }
}
