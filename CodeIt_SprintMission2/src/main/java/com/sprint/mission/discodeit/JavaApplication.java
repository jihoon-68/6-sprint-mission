package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class JavaApplication {

    public static void main(String[] args) {
        initialize();

        UserService userService = new FileUserService();
        ChannelService channelService = new FileChannelService();
        MessageService messageService = new FileMessageService(userService);

        // 각 도메인에 대한 CRUD 테스트 진행
        testUserCRUD(userService);
        testChannelCRUD(channelService);
        testMessageCRUD(messageService, userService);
    }

    /**
     * 테스트 실행 전, 이전에 저장된 ser 파일들을 삭제하여 초기화합니다.
     */
    private static void initialize() {
        System.out.println("--- 🧹 데이터 파일 초기화를 시작합니다 ---");
        boolean usersDeleted = new File("users.ser").delete();
        boolean channelsDeleted = new File("channels.ser").delete();
        boolean messagesDeleted = new File("messages.ser").delete();

        System.out.println("users.ser 삭제: " + (usersDeleted ? "성공" : "파일 없음"));
        System.out.println("channels.ser 삭제: " + (channelsDeleted ? "성공" : "파일 없음"));
        System.out.println("messages.ser 삭제: " + (messagesDeleted ? "성공" : "파일 없음"));
        System.out.println("--- ✅ 초기화 완료 ---\n");
    }

    /**
     * User 도메인에 대한 CRUD 연산을 테스트합니다.
     * @param userService UserService 인스턴스
     */
    private static void testUserCRUD(UserService userService) {
        System.out.println("========== 👤 User CRUD 테스트 시작 ==========");

        // CREATE
        System.out.println("\n--- 1. User 생성 ---");
        User user1 = userService.createUser("Alice", "alice@email.com");
        User user2 = userService.createUser("Bob", "bob@email.com");
        System.out.println("생성된 User: " + user1);
        System.out.println("생성된 User: " + user2);

        // READ (ALL)
        System.out.println("\n--- 2. 모든 User 조회 ---");
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> System.out.println("조회된 User: " + user));

        // READ (ONE)
        System.out.println("\n--- 3. ID로 User 조회 ---");
        User foundUser = userService.getUserById(user1.getId());
        System.out.println("ID로 조회된 User: " + foundUser);

        // UPDATE
        System.out.println("\n--- 4. User 정보 수정 ---");
        System.out.println("수정 전 User: " + user1);
        User updatedUser = userService.updateUser(user1.getId(), "Alice Kim", "alice.kim@email.com");
        System.out.println("수정 후 User: " + updatedUser);

        // DELETE
        System.out.println("\n--- 5. User 삭제 ---");
        System.out.println("삭제할 User: " + user2.getName());
        userService.deleteUser(user2.getId());

        // 최종 조회
        System.out.println("\n--- 6. 최종 User 목록 확인 ---");
        userService.getAllUsers().forEach(user -> System.out.println("남아있는 User: " + user));
        System.out.println("========== 👤 User CRUD 테스트 완료 ==========\n");
    }

    /**
     * Channel 도메인에 대한 CRUD 연산을 테스트합니다.
     * @param channelService ChannelService 인스턴스
     */
    private static void testChannelCRUD(ChannelService channelService) {
        System.out.println("========== 💬 Channel CRUD 테스트 시작 ==========");

        // CREATE
        System.out.println("\n--- 1. Channel 생성 ---");
        Channel channel1 = channelService.createChannel("일반", "자유롭게 대화하는 채널");
        Channel channel2 = channelService.createChannel("개발", "개발 관련 이야기 채널");
        System.out.println("생성된 Channel: " + channel1);
        System.out.println("생성된 Channel: " + channel2);

        // READ (ALL)
        System.out.println("\n--- 2. 모든 Channel 조회 ---");
        channelService.getAllChannels().forEach(ch -> System.out.println("조회된 Channel: " + ch));

        // READ (ONE)
        System.out.println("\n--- 3. ID로 Channel 조회 ---");
        Channel foundChannel = channelService.getChannelById(channel1.getId());
        System.out.println("ID로 조회된 Channel: " + foundChannel);

        // UPDATE
        System.out.println("\n--- 4. Channel 정보 수정 ---");
        System.out.println("수정 전 Channel: " + channel1);
        Channel updatedChannel = channelService.updateChannel(channel1.getId(), "공지사항", "중요 공지를 전달하는 채널");
        System.out.println("수정 후 Channel: " + updatedChannel);

        // DELETE
        System.out.println("\n--- 5. Channel 삭제 ---");
        System.out.println("삭제할 Channel: " + channel2.getName());
        channelService.deleteChannel(channel2.getId());

        // 최종 조회
        System.out.println("\n--- 6. 최종 Channel 목록 확인 ---");
        channelService.getAllChannels().forEach(ch -> System.out.println("남아있는 Channel: " + ch));
        System.out.println("========== 💬 Channel CRUD 테스트 완료 ==========\n");
    }

    /**
     * Message 도메인에 대한 CRUD 연산을 테스트합니다.
     * @param messageService MessageService 인스턴스
     * @param userService UserService 인스턴스 (테스트용 사용자 생성을 위해)
     */
    private static void testMessageCRUD(MessageService messageService, UserService userService) {
        System.out.println("========== 📨 Message CRUD 테스트 시작 ==========");

        // 테스트용 사용자 생성
        User sender = userService.createUser("Sender", "sender@email.com");
        User receiver = userService.createUser("Receiver", "receiver@email.com");

        // CREATE
        System.out.println("\n--- 1. Message 생성 ---");
        Message message1 = messageService.createMessage(sender.getId(), receiver.getId(), "안녕하세요!");
        Message message2 = messageService.createMessage(receiver.getId(), sender.getId(), "네, 안녕하세요!");
        System.out.println("생성된 Message: " + message1);
        System.out.println("생성된 Message: " + message2);

        // READ (ALL)
        System.out.println("\n--- 2. 모든 Message 조회 ---");
        messageService.getAllMessages().forEach(msg -> System.out.println("조회된 Message: " + msg));

        // READ (ONE)
        System.out.println("\n--- 3. ID로 Message 조회 ---");
        Message foundMessage = messageService.getMessageById(message1.getId());
        System.out.println("ID로 조회된 Message: " + foundMessage);

        // UPDATE
        System.out.println("\n--- 4. Message 내용 수정 ---");
        System.out.println("수정 전 Message: " + message1);
        Message updatedMessage = messageService.updateMessage(message1.getId(), "안녕하세요! 반갑습니다.");
        System.out.println("수정 후 Message: " + updatedMessage);

        // DELETE
        System.out.println("\n--- 5. Message 삭제 ---");
        System.out.println("삭제할 Message ID: " + message2.getId());
        messageService.deleteMessage(message2.getId());

        // 최종 조회
        System.out.println("\n--- 6. 최종 Message 목록 확인 ---");
        messageService.getAllMessages().forEach(msg -> System.out.println("남아있는 Message: " + msg));
        System.out.println("========== 📨 Message CRUD 테스트 완료 ==========\n");
    }
}
