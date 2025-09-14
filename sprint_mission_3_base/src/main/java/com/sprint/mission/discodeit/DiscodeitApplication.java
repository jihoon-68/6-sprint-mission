package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.DTO.Channel.CreateChannelRequest;
import com.sprint.mission.discodeit.DTO.Message.CreateMessageRequest;
import com.sprint.mission.discodeit.DTO.User.UserResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DiscodeitApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		// 셋업
		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);
		// 테스트
		messageCreateTest(messageService, channel, user);

	}

	static User setupUser(UserService userService) {
		UserResponse userResponse = new UserResponse(null,"woody", "woody@codeit.com", "woody1234","");
		User user = userService.create(userResponse);
		return user;
	}

	static Channel setupChannel(ChannelService channelService) {
		CreateChannelRequest request = new CreateChannelRequest(null,"공지", "공지 채널입니다.");
		Channel channel = channelService.createPublicChannel(request);
		return channel;
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		CreateMessageRequest request = new CreateMessageRequest("안녕하세요.", channel.getId(), author.getId(), null);
		Message message = messageService.create(request);
		System.out.println("메시지 생성: " + message.getId());
	}

}
