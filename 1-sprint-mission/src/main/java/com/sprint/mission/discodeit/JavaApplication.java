package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
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
        UserService userSvc = new JCFUserService();
        ChannelService chSvc = new JCFChannelService();
        MessageService msgSvc = new JCFMessageService(userSvc, chSvc);

        // ====== USER ======
        System.out.println("==== USER ====");
        // 생성자: User(String activeType, String name, String nickname, String description, String[] badges)
        User u1 = new User("online", "Alice", "ali", "백엔드 개발자", new String[]{"PRO", "EARLY"});
        User u2 = new User("offline", "Bob", "bobby", "프론트엔드 개발자", new String[]{"NEWBIE"});
        // 등록
        userSvc.create(u1);
        userSvc.create(u2);
        System.out.println("[등록] " + u1.getName() + " / " + u2.getName());

        // 조회(단건, 다건)
        System.out.println("[단건조회] " + userSvc.findById(u1.getId()).getName());

        System.out.println("[다건조회]");
        userSvc.findAll().forEach(user -> System.out.println(user.getName()));

        userSvc.update(u1.getId(), "Alice Kim", "ali-k");

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + userSvc.findById(u1.getId()).getName());

        if (userSvc.delete(u1.getId())) {
            System.out.println(u1.getName() + "삭제완료!");
        } else {
            System.out.println(u1.getName() +" 삭제실패...");
        }

        // ====== CHANNEL ======
        System.out.println("\n==== CHANNEL ====");
        List<User> ch1Users = new ArrayList<>(List.of(u1, u2));
        List<Message> ch1Messages = new ArrayList<>();

        Channel c1 = new Channel(ch1Users, "codeit", ch1Messages);
        Channel c2 = new Channel(ch1Users, "general", ch1Messages);
        chSvc.create(c1);
        chSvc.create(c2);
        System.out.println("[등록] channel id = " + c1.getId());

        // 조회(단건, 다건)
        System.out.println("[단건조회] " + chSvc.findById(c1.getId()).getName());
        System.out.println("[다건조회] " + chSvc.findAll());

        // 수정 (Channel.update(String name) 시그니처에 맞춤)
        chSvc.update(c1.getId(), "codeit-chat", null);

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + chSvc.findById(c1.getId()).getName());

        if (chSvc.delete(c2.getId())) {
            System.out.println(c1.getName() + "채널 삭제완료!");
        } else {
            System.out.println(c1.getName() +" 채널 삭제실패...");
        }

        // ====== MESSAGE ======
        System.out.println("\n==== MESSAGE ====");
        // Message는 이전에 만든 엔티티( userId, channelId, content )를 쓴다고 가정

        Message m1 = new Message(u1, "안녕", c1);
        Message m2 = new Message(u1, "반가워", c1);

        Message m3 = new Message(u1, "여기도 안녕", c2);
        Message m4 = new Message(u2, "u2의 안녕", c2);

        msgSvc.create(m1);
        msgSvc.create(m2);
        msgSvc.create(m3);
        msgSvc.create(m4);

        Message found = msgSvc.findById(m1.getId());
        if (found != null) {
            System.out.println("찾은 메세지: " + found);
        } else {
            System.out.println("Message not found");
        }
//        // 조회(단건/다건/채널별)
        System.out.println("[단건조회] " + msgSvc.findById(m1.getId()).getContent());
        System.out.println("[다건조회] ");
        msgSvc.findAll().stream().forEach(message -> System.out.println(message.getContent()));

        System.out.println("[채널별조회] " + msgSvc.findByChannelId(c1.getId()));

        // 수정
        msgSvc.update(m1.getId(), "안녕하세요!! (수정)");

        // 수정된 데이터 조회
        System.out.println("[수정후조회] " + msgSvc.findById(m1.getId()).getContent());

        // ====== 삭제 & 검증 ======
        System.out.println("\n==== DELETE & VERIFY ====");
        boolean deletedMsg = msgSvc.delete(m2.getId());
        System.out.println("[삭제] m2 deleted? " + deletedMsg);
        System.out.println("[삭제검증] " + msgSvc.findById(m2.getId())); // Optional.empty 기대

        boolean deletedUser = userSvc.delete(u2.getId());
        System.out.println("[삭제] u2 deleted? " + deletedUser);
        System.out.println("[삭제검증] " + userSvc.findById(u2.getId())); // Optional.empty 기대
    }
}