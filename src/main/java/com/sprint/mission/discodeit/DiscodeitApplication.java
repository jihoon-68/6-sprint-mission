package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.Enum.ChannelType;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.basic.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {


    static User setupUser(BasicUserService basicUserService, BasicBinaryContentService binaryContentService) {
        UUID uuid = UUID.randomUUID();
        return basicUserService.create(new CreateUserDTO("woody1",20 ,"woody1@codeit.com",uuid,"aqswde1"));
    }
    static Channel setupChannelPrivate(BasicChannelService basicChannelService, List<UUID> usersID) {

        return basicChannelService.createPrivate(new CreatePrivateChannelDTO(usersID,ChannelType.PRIVATE));
    }
    static Channel setupChannelPublic(BasicChannelService basicChannelService) {
        return basicChannelService.createPublic(new CreatePublicChannelDTO("공개 채팅","테스트",ChannelType.PUBLIC));
    }

    static void messageCreateTest(BasicMessageService basicMessageService, UUID channelId, UUID authorId) {
        Message message1 = basicMessageService.create(new CreateMessageDTO(channelId,authorId,"안녕하세요",new ArrayList<byte[]>()));

        System.out.println("메시지 생성: " + message1.getId());
    }

	public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(DiscodeitApplication.class, args);
        BasicUserService basicUserService = context.getBean(BasicUserService.class);
        BasicChannelService basicChannelService = context.getBean(BasicChannelService.class);
        BasicMessageService basicMessageService = context.getBean(BasicMessageService.class);
        BasicReadStatusService basicReadStatusService = context.getBean(BasicReadStatusService.class);
        BasicUserStatusService basicUserStatusService = context.getBean(BasicUserStatusService.class);
        BasicBinaryContentService basicBinaryContentService  = context.getBean(BasicBinaryContentService.class);



        if(basicUserService.findAll().isEmpty()){
            List<UUID> users = List.of(new UUID[]{
                    basicUserService.create(new CreateUserDTO("woody2", 20, "woody2@codeit.com", UUID.randomUUID(), "aqswde2")).getId(),
                    basicUserService.create(new CreateUserDTO("woody3", 20, "woody3@codeit.com", UUID.randomUUID(), "aqswde3")).getId(),
                    basicUserService.create(new CreateUserDTO("woody4", 20, "woody4@codeit.com", UUID.randomUUID(), "aqswde4")).getId(),
                    basicUserService.create(new CreateUserDTO("woody5", 20, "woody5@codeit.com", UUID.randomUUID(), "aqswde5")).getId(),
            });

            User user = setupUser(basicUserService, basicBinaryContentService);
            Channel channelPrivate = setupChannelPrivate(basicChannelService , users);
            Channel channelPublic = setupChannelPublic(basicChannelService);

        }

        UUID user = basicUserService.findEmail("woody2@codeit.com").id();

        FindChannelDTO channelPrivate = basicChannelService.findAllByUserId(user).stream()
                .filter(channel -> channel.channel().getType().equals(ChannelType.PRIVATE))
                .findAny()
                .orElseThrow(() -> new NullPointerException("user null"));
        FindChannelDTO channelPublic = basicChannelService.findAllByUserId(user).stream()
                .filter(channel -> channel.channel().getType().equals(ChannelType.PUBLIC))
                .findAny()
                .orElseThrow(() -> new NullPointerException("user null"));

        messageCreateTest(basicMessageService,channelPrivate.channel().getId(),user);
        messageCreateTest(basicMessageService,channelPublic.channel().getId(),user);

    }
}
