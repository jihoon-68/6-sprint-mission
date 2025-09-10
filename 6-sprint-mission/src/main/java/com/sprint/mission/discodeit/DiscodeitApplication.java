package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.channeldto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.messagedto.CreateMessageDto;
import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class DiscodeitApplication {

	static User setupUser(UserService userService) {
		CreateUserDto createUserDto = new CreateUserDto("woody", "woody@codeit.com", "woody1234");
		User user = userService.create(createUserDto);
		return user;
	}

	static Channel setupChannel(ChannelService channelService) {
		CreateChannelDto createChannelDto = new CreateChannelDto(ChannelType.PUBLIC, "공지", "공지 채널입니다.",null);
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
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);
		UserService userService = context.getBean(BasicUserService.class);
		ChannelService channelService = context.getBean(BasicChannelService.class);
		MessageService messageService = context.getBean(BasicMessageService.class);

		// 셋업
		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);
		// 테스트
		messageCreateTest(messageService, channel, user);

	}

}
