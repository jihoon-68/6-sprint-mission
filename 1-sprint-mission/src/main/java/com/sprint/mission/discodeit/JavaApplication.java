package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.*;

import java.net.URL;
import java.util.*;

public class JavaApplication {

    public static void main(String[] args) {
        try {
            runApp();
        } catch (Throwable t) {
            System.err.println("=== Uncaught exception in main ===");
            t.printStackTrace();              // ← 예외 종류/라인 위치 출력
            System.exit(1);
        }
    }

    public static void runApp() throws Exception {
        // === 서비스 준비 ===

        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        UserService userSvc = new BasicUserService(userRepository);
        ChannelService chSvc = new BasicChannelService(channelRepository, userRepository);
        MessageService msgSvc = new BasicMessageService(messageRepository, userRepository, channelRepository);

        // ====== USER ======
        System.out.println("==== USER ====");
        // 등록
        var u1 = userSvc.create("Alice", "1234567", "ali", "백엔드 개발자", "백엔드 개발자입니다", Arrays.asList("PRO", "EARLY"));
        var u2 = userSvc.create("Bob", "qwerasdf", "bobby", "프론트엔드 개발자", "프론트엔드 개발자입니다", Arrays.asList("NEWBIE"));
        var u3 = userSvc.create("Tom", "09876543", "tommy", "AI 개발자", "AI 개발자입니다", Arrays.asList("Artificial"));
        System.out.println("[등록] " + u1.getName() + " / " + u2.getName());

        // 조회(단건, 다건)
        System.out.println("[단건조회] " + userSvc.findById(u1.getId()).getName());

        System.out.println("[다건조회]");
        userSvc.findAll().forEach(user -> System.out.println(user.getName()));
        var users = userRepository.findAll();

        userSvc.update(u1.getId(), "Alice Kim", "ali-k");

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + userSvc.findById(u1.getId()).getName());

        if (userSvc.delete(u3.getId())) {
            System.out.println(u3.getName() + "삭제완료!");
        } else {
            System.out.println(u3.getName() +" 삭제실패...");
        }

        //   ====== CHANNEL ======
        System.out.println("\n==== CHANNEL ====");
        List<User> ch1Users = new ArrayList<>(List.of(u1, u2));
        List<Message> ch1Messages = new ArrayList<>();

        Channel c1 = null;
        Channel c2 = null;
        Channel c3 = null;

        try {
            c1 = chSvc.create("codeit", ch1Users, ch1Messages);
            c2 = chSvc.create("general", ch1Users, ch1Messages);
            c3 = chSvc.create("game", ch1Users, ch1Messages);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println("[등록] channel id = " + c1.getId());

        //   조회(단건, 다건)
        System.out.println("[단건조회] " + chSvc.findById(c1.getId()).getName());

        System.out.println("[다건조회] " + chSvc.findAll());
        chSvc.update(c1.getId(), "codeit-chat", null);

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + chSvc.findById(c1.getId()).getName());

        if (chSvc.delete(c3.getId())) {
            System.out.println(c3.getName() + "채널 삭제완료!");
        } else {
            System.out.println(c3.getName() +" 채널 삭제실패...");
        }

        // ====== MESSAGE ======
        System.out.println("\n==== MESSAGE ====");

        Message m1 = null;
        Message m2 = null;
        Message m3 = null;
        Message m4 = null;

        try {
            m1 = msgSvc.create(u1, "안녕", c1);
            m2 = msgSvc.create(u1, "반가워", c1);
            m3 = msgSvc.create(u1, "여기도 안녕", c2);
            m4 = msgSvc.create(u2, "u2의 안녕", c2);
        } catch (NoSuchElementException e) {
            System.out.println(e.getLocalizedMessage());
        }

        Message found = msgSvc.findById(m1.getId());
        if (found != null) {
            System.out.println("찾은 메세지: " + found);
        } else {
            System.out.println("Message not found");
        }
        // 조회(단건/다건/채널별)
        System.out.println("[단건조회] " + msgSvc.findById(m1.getId()).getContent());
        System.out.println("[다건조회] ");
        msgSvc.findAll().stream().forEach(message -> System.out.println(message.getContent()));

        // 수정
        msgSvc.update(m1.getId(), "안녕하세요!! (수정)");

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + msgSvc.findById(m1.getId()).getContent());

        // ====== 삭제 & 검증 ======
        System.out.println("\n==== DELETE & VERIFY ====");
        boolean deletedMsg = msgSvc.delete(m2.getId());
        System.out.println("[삭제] m2 deleted? " + deletedMsg);

        try {
            System.out.println("[삭제검증] " + msgSvc.findById(m2.getId()));
        } catch (NoSuchElementException e) {
            System.out.println("[삭제검증] 메시지를 찾을 수 없습니다. (" + e.getMessage() + ")");
        }
    }
}