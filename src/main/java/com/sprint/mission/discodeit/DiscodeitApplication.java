package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.Enum.ChannelType;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DiscodeitApplication {

    static User setupUser(BasicUserService basicUserService) {
        return basicUserService.create("woody",20 ,"woody@codeit.com");
    }

    static Channel setupChannel(BasicChannelService basicChannelService) {
        return basicChannelService.create("공지", "sfdadfasf", ChannelType.PRIVATE);
    }

    static void messageCreateTest(BasicMessageService basicMessageService, Channel channel, User author) {
        Message message = basicMessageService.create(channel.getId(),author.getId(),"안녕하세요");
        System.out.println("메시지 생성: " + message.getId());
    }

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(DiscodeitApplication.class, args);

        //File 저장 방식
        FileUserRepository fileUserRepository = context.getBean(FileUserRepository.class);
        FileMessageRepository fileMessageRepository = context.getBean(FileMessageRepository.class);
        FileChannelRepository fileChannelRepository = context.getBean(FileChannelRepository.class);

        BasicUserService fileBasicUserService = context.getBean(BasicUserService.class);
        BasicChannelService fileBasicChannelService = context.getBean(BasicChannelService.class);
        BasicMessageService fileBasicMessageService = context.getBean(BasicMessageService.class);


        User FileUser = setupUser(fileBasicUserService);
        Channel FileChannel = setupChannel(fileBasicChannelService);
        // 테스트
        messageCreateTest(fileBasicMessageService, FileChannel, FileUser);


	}


}
