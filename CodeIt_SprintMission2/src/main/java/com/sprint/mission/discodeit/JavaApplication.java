package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;

import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {

        JCFUserService userService = new JCFUserService();

        System.out.println("[등록] GURU");
        String name = "GURU";
        String email = "guru@example.com";
        userService.createUser(name, email);


        UUID userId = null;
        for (User u : userService.getAllUsers()) {
            if (name.equals(u.getName()) && email.equals(u.getEmail())) {
                userId = u.getId();
                break;
            }
        }


        System.out.println("[조회 - 단건]");
        User found = userService.getUserById(userId);
        System.out.println(found);

        System.out.println("[조회 - 다건]");
        for (User u : userService.getAllUsers()) {
            System.out.println(u);
        }

        System.out.println("[수정]");
        User updated = userService.updateUser(userId, "GURU_UPDATED", "guru_updated@example.com");
        System.out.println(updated);

        System.out.println("[수정된 데이터 조회]");
        User updatedFound = userService.getUserById(userId);
        System.out.println(updatedFound);

        System.out.println("[삭제]");
        boolean deleted = userService.deleteUser(userId);
        System.out.println("삭제 결과: " + deleted);

        System.out.println("[조회를 통해 삭제되었는지 확인]");
        User afterDelete = userService.getUserById(userId);
        System.out.println(afterDelete == null ? "삭제 확인: 더 이상 조회되지 않습니다." : afterDelete.toString());


        // 메시지 생성 시 사용자 존재 검증 데모
        System.out.println("\n[메시지 생성 검증 데모]");
        User sender = userService.createUser("SENDER", "sender@example.com");
        User receiver = userService.createUser("RECEIVER", "receiver@example.com");
        JCFMessageService messageService = new JCFMessageService(userService);

        System.out.println("- 정상 케이스: 존재하는 사용자들로 메시지 생성");
        Message ok = messageService.createMessage(sender.getId(), receiver.getId(), "Hello");
        System.out.println(ok);

        System.out.println("- 실패 케이스: 존재하지 않는 발신자 ID로 메시지 생성");
        UUID fakeSender = UUID.randomUUID();
        Message fail1 = messageService.createMessage(fakeSender, receiver.getId(), "Should fail by sender");
        System.out.println(fail1 == null ? "생성 실패 확인(발신자)" : fail1.toString());

        System.out.println("- 실패 케이스: 존재하지 않는 수신자 ID로 메시지 생성");
        UUID fakeReceiver = UUID.randomUUID();
        Message fail2 = messageService.createMessage(sender.getId(), fakeReceiver, "Should fail by receiver");
        System.out.println(fail2 == null ? "생성 실패 확인(수신자)" : fail2.toString());
    }
}
