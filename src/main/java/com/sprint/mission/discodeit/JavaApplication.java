package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.*;
import com.sprint.mission.discodeit.service.jcf.*;
import com.sprint.mission.discodeit.service.file.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.jcf.*;
import com.sprint.mission.discodeit.repository.file.*;

import java.util.*;

public class JavaApplication {
    static User setupUser(UserService userService) {
        User user = userService.create("woody", "woody@codeit.com", "woody1234");
        return user;
    }

    static Channel setupChannel(ChannelService channelService) {
        Channel channel = channelService.create(ChannelType.PUBLIC, "공지", "공지 채널입니다.");
        return channel;
    }

    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
        Message message = messageService.create("안녕하세요.", channel.getId(), author.getId());
        System.out.println("메시지 생성: " + message.getId());
    }

    static void runCrudScenario(UserService userService, ChannelService channelService, MessageService messageService) {
        System.out.println("===== CRUD 시나리오 시작 =====");
        // 등록
        User user = setupUser(userService);
        Channel channel = setupChannel(channelService);
        messageCreateTest(messageService, channel, user);

        // 조회 (단건/다건)
        System.out.println("사용자 단건 조회: " + userService.findById(user.getId()).map(User::getEmail).orElse("없음"));
        System.out.println("채널 전체 조회 수: " + channelService.findAll().size());
        System.out.println("메시지 전체 조회 수: " + messageService.findAll().size());

        // 수정
        userService.update(user.getId(), "woody", "woody@codeit.com", "newPass");
        channelService.update(channel.getId(), ChannelType.PRIVATE, "비밀", "비밀 채널");
        Message anyMsg = messageService.findAll().get(0);
        messageService.update(anyMsg.getId(), "수정된 인사", channel.getId(), user.getId());

        // 수정된 데이터 조회
        System.out.println("수정된 사용자 패스워드: " + userService.findById(user.getId()).get().getPassword());
        System.out.println("수정된 채널 타입: " + channelService.findById(channel.getId()).get().getType());
        System.out.println("수정된 메시지 내용: " + messageService.findById(anyMsg.getId()).get().getContent());

        // 삭제 및 확인
        boolean deletedMsg = messageService.delete(anyMsg.getId());
        System.out.println("메시지 삭제됨? " + deletedMsg);
        System.out.println("메시지 존재 여부: " + messageService.findById(anyMsg.getId()).isPresent());
        System.out.println("===== CRUD 시나리오 종료 =====\n");
    }

    public static void main(String[] args) {
        // ── 선택 1) JCF*Service 직접 사용 (빠른 시작) ───────────────────────────
        UserService jcfUserService = new JCFUserService();
        ChannelService jcfChannelService = new JCFChannelService();
        MessageService jcfMessageService = new JCFMessageService(jcfUserService, jcfChannelService);
        runCrudScenario(jcfUserService, jcfChannelService, jcfMessageService);

        // ── 선택 2) File*Service 사용 (File IO 직렬화) ──────────────────────────
        String baseDir = System.getProperty("user.dir") + "/data"; // ./data/*.ser
        UserService fileUserService = new FileUserService(baseDir);
        ChannelService fileChannelService = new FileChannelService(baseDir);
        MessageService fileMessageService = new FileMessageService(baseDir, fileUserService, fileChannelService);
        runCrudScenario(fileUserService, fileChannelService, fileMessageService);

        // ── 선택 3) 관심사 분리: Repository + Basic*Service (DI) ───────────────
        // 3-A) JCF Repository 기반
        UserRepository userRepo = new JCFUserRepository();
        ChannelRepository channelRepo = new JCFChannelRepository();
        MessageRepository messageRepo = new JCFMessageRepository();
        UserService basicUserService = new BasicUserService(userRepo);
        ChannelService basicChannelService = new BasicChannelService(channelRepo);
        MessageService basicMessageService = new BasicMessageService(messageRepo, userRepo, channelRepo);
        runCrudScenario(basicUserService, basicChannelService, basicMessageService);

        // 3-B) File Repository 기반 (영속화)
        UserRepository fUserRepo = new FileUserRepository(baseDir);
        ChannelRepository fChannelRepo = new FileChannelRepository(baseDir);
        MessageRepository fMessageRepo = new FileMessageRepository(baseDir);
        UserService basicUserService2 = new BasicUserService(fUserRepo);
        ChannelService basicChannelService2 = new BasicChannelService(fChannelRepo);
        MessageService basicMessageService2 = new BasicMessageService(fMessageRepo, fUserRepo, fChannelRepo);
        runCrudScenario(basicUserService2, basicChannelService2, basicMessageService2);
    }
}
