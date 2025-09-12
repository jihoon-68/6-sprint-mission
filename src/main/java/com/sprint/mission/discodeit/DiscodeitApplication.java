package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.dto.PublicChannelDto;
import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@ConfigurationPropertiesScan
@EnableAspectJAutoProxy
public class DiscodeitApplication {

    private static final Logger log = LoggerFactory.getLogger(DiscodeitApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);
        // 서비스 초기화
        // TODO context에서 Bean을 조회하여 각 서비스 구현체 할당 코드 작성하세요.
        UserService userService = context.getBean(UserService.class);
        ChannelService channelService = context.getBean(ChannelService.class);
        MessageService messageService = context.getBean(MessageService.class);

        // ...
        // 셋업
        UserDto.Response userResponse = setupUser(userService);
        PublicChannelDto.Response channelResponse = setupChannel(channelService);
        // 테스트
        messageCreateTest(messageService, channelResponse.id(), userResponse.id());
    }

    static UserDto.Response setupUser(UserService userService) {
        var request = new UserDto.Request("woody", "woody@codeit.com", "woody1234", "woody", new byte[0]);
        return userService.create(request);
    }

    static PublicChannelDto.Response setupChannel(ChannelService channelService) {
        var request = new PublicChannelDto.Request("공지", "공지 채널입니다.");
        return channelService.create(request);
    }

    static void messageCreateTest(MessageService messageService, UUID channelId, UUID authorId) {
        var request = new MessageDto.Request("안녕하세요.", channelId, authorId, Collections.emptyList());
        MessageDto.Response messageResponse = messageService.create(request);
        log.info("메시지 생성: {}", messageResponse.id());
    }
}
