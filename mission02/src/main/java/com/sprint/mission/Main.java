package com.sprint.mission;


import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelInterface;
import com.sprint.mission.discodeit.service.file.FileMessageInterface;
import com.sprint.mission.discodeit.service.file.FileUserInterface;
import com.sprint.mission.discodeit.service.jcf.JCFChannelInterface;
import com.sprint.mission.discodeit.service.jcf.JCFMessageInterface;
import com.sprint.mission.discodeit.service.jcf.JCFUserInterface;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        testCaseJCFUser();
//        testCaseJCFChannel();
//        testCaseJCFMessage();

        testCaseFileUser();
        testCaseFileChannel();
        testCaseFileMessage();

    }

    private static void testCaseJCFUser() {
        System.out.println("Testing User functionality...");
        UserService userService = new JCFUserInterface();
        User user = new User("testUser");
        userService.addUser(user);
        System.out.println("유저 추가: " + user.getUserName());
        // 유저 추가 테스트
        try {
            User foundUser = userService.findUserByName("testUser");
            System.out.println("유저 찾기: " + foundUser);
        } catch (NotFoundException e) {
            System.out.println("유저 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 추가 예외 사항(중복된 유저 이름)
        try {
            userService.addUser(new User("testUser"));
        } catch (DuplicateException e) {
            System.out.println("유저 추가 중복 에러(사용자 이름 중복): " + e.getMessage());
        }

        // 유저 찾기 테스트
        try {
            User foundUserById = userService.findUserById(user.getUuid());
            System.out.println("유저 ID로 찾기: " + foundUserById);
        } catch (NotFoundException e) {
            System.out.println("유저 ID로 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 모든 유저 찾기 테스트
        try {
            userService.addUser(new User("anotherUser"));
            userService.addUser(new User("thirdUser"));
            userService.addUser(new User("fourthUser"));
            System.out.println("모든 유저 찾기:");
            userService.findAllUsers().forEach(u -> System.out.println("유저: " + u));
        } catch (NotFoundException e) {
            System.out.println("모든 유저 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 ID로 찾기 테스트
        try {
            User foundUserById = userService.findUserById(user.getUuid());
            System.out.println("유저 ID로 찾기 성공: " + foundUserById);
        } catch (NotFoundException e) {
            System.out.println("유저 단일 조회 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 이름으로 찾기 테스트
        try {
            User foundUserByName = userService.findUserByName("testUser");
            System.out.println("유저 이름으로 찾기: " + foundUserByName);
        } catch (NotFoundException e) {
            System.out.println("유저 이름으로 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 업데이트 테스트
        try {
            userService.updateUser("testUser", "updatedUser");
            User updatedUser = userService.findUserByName("updatedUser");
            System.out.println("유저 업데이트: " + updatedUser);
        } catch (NotFoundException e) {
            System.out.println("유저 업데이트 에러(사용자가 없음): " + e.getMessage());
        } catch (DuplicateException e) {
            System.out.println("유저 업데이트 에러(중복된 사용자 이름): " + e.getMessage());
        }

        // 유저 삭제 테스트
        try {
            userService.removeUser(user);
            System.out.println("유저 삭제 성공: " + user);
        } catch (NotFoundException e) {
            System.out.println("유저 삭제 에러(사용자가 없음): " + e.getMessage());
        }

    }

    private static void testCaseJCFChannel() {
        System.out.println("Testing Channel functionality...");
        ChannelService channelService = new JCFChannelInterface();
        UUID id = UUID.randomUUID();
        Channel channel = new Channel("testChannel", "This is a test channel");
        // 채널 추가 테스트
        try {
            channelService.addChannel(channel);
            System.out.println("채널 추가 성공: " + channel);
        } catch (DuplicateException e) {
            System.out.println("채널 추가 에러(중복된 채널 이름): " + e.getMessage());
        }

        // 채널 추가 예외 사항(중복된 채널 이름)
        try {
            channelService.addChannel(new Channel("testChannel", "This is a test channel"));
        } catch (DuplicateException e) {
            System.out.println("채널 추가 중복 에러(채널 이름 중복): " + e.getMessage());
        }

        // 채널 이름으로 찾기 테스트
        try {
            Channel foundChannel = channelService.findChannelByName("testChannel");
            System.out.println("채널 찾기 성공: " + foundChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 찾기 에러(채널이 없음): " + e.getMessage());
        }

        // 채널 ID로 찾기 테스트
        try {
            Channel foundChannelById = channelService.findChannelById(id);
            System.out.println("채널 ID로 찾기 성공: " + foundChannelById);
        } catch (NotFoundException e) {
            System.out.println("채널 ID로 찾기 에러(채널이 없음): " + e.getMessage());
        }

        // 모든 채널 찾기 테스트
        try {
            channelService.addChannel(new Channel("anotherChannel", "This is another channel"));
            channelService.addChannel(new Channel("thirdChannel", "This is the third channel"));
            System.out.println("모든 채널 찾기:");
            channelService.findAllChannels().forEach(c -> System.out.println("채널: " + c));
        } catch (NotFoundException e) {
            System.out.println("모든 채널 찾기 에러(채널이 없음): " + e.getMessage());
        }

        // 채널 업데이트 테스트
        try {
            channelService.updateChannel(channel.getChannelName(), "updatedChannel");
            Channel updatedChannel = channelService.findChannelByName("updatedChannel");
            System.out.println("채널 업데이트 성공: " + updatedChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 업데이트 에러(채널이 없음): " + e.getMessage());
        } catch (DuplicateException e) {
            System.out.println("채널 업데이트 에러(중복된 채널 이름): " + e.getMessage());
        }
        // 채널 설명 업데이트 테스트
        try {
            channelService.updateChannelDescription(channel.getChannelName(), "This is an updated channel description");
            Channel updatedChannel = channelService.findChannelById(id);
            System.out.println("채널 설명 업데이트 성공: " + updatedChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 설명 업데이트 에러(채널이 없음): " + e.getMessage());
        }
        // 채널 삭제 테스트
        try {
            channelService.removeChannel(channel);
            System.out.println("채널 삭제 성공: " + channel);
        } catch (NotFoundException e) {
            System.out.println("채널 삭제 에러(채널이 없음): " + e.getMessage());
        }
    }

    private static void testCaseJCFMessage() {
        System.out.println("Testing Message functionality...");
        MessageService messageService = new JCFMessageInterface();

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        String messageContent = "Hello, this is a test message!";
        Message message = new Message(messageContent, senderId, receiverId);
        // 메시지 추가 테스트
        try {
            messageService.addMessage(message);
            System.out.println("메시지 추가 성공: " + message);
        } catch (Exception e) {
            System.out.println("메시지 추가 에러: " + e.getMessage());
        }
        // 메세지 전체 찾기 테스트
        try {
            messageService.addMessage(new Message("Another test message", senderId, receiverId));
            messageService.addMessage(new Message("Third test message", senderId, receiverId));
            System.out.println("모든 메시지 찾기:");
            messageService.findAllMessages().forEach(m -> System.out.println("메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("모든 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 ID로 찾기 테스트
        try {
            Message foundMessage = messageService.findMessageById(message.getUuid());
            System.out.println("메시지 ID로 찾기 성공: " + foundMessage);
        } catch (NotFoundException e) {
            System.out.println("메시지 ID로 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 보낸 사람으로 찾기 테스트
        try {
            messageService.addMessage(new Message("Message from sender", senderId, receiverId));
            messageService.findMessagesBySender(senderId).forEach(m -> System.out.println("보낸 메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("보낸 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 받는 사람으로 찾기 테스트
        try {
            messageService.addMessage(new Message("Message to receiver", senderId, receiverId));
            messageService.findMessagesByReceiver(receiverId).forEach(m -> System.out.println("받은 메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("받은 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 업데이트 테스트
        try {
            String newMessageContent = "This is an updated message content.";
            messageService.updateMessage(message.getUuid(), newMessageContent);
            Message updatedMessage = messageService.findMessageById(message.getUuid());
            System.out.println("메시지 업데이트 성공: " + updatedMessage);
        } catch (NotFoundException e) {
            System.out.println("메시지 업데이트 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 삭제 테스트
        try {
            messageService.removeMessage(message);
            System.out.println("메시지 삭제 성공: " + message);
        } catch (NotFoundException e) {
            System.out.println("메시지 삭제 에러(메시지가 없음): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("메시지 삭제 에러: " + e.getMessage());
        }
    }

    private static void testCaseFileUser() {
        System.out.println("Testing User functionality...");
        UserService userService = new FileUserInterface();
        User user = new User("testUser");
        userService.addUser(user);
        System.out.println("유저 추가: " + user.getUserName());
        // 유저 추가 테스트
        try {
            User foundUser = userService.findUserByName("testUser");
            System.out.println("유저 찾기: " + foundUser);
        } catch (NotFoundException e) {
            System.out.println("유저 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 추가 예외 사항(중복된 유저 이름)
        try {
            userService.addUser(new User("testUser"));
        } catch (DuplicateException e) {
            System.out.println("유저 추가 중복 에러(사용자 이름 중복): " + e.getMessage());
        }

        // 유저 찾기 테스트
        try {
            User foundUserById = userService.findUserById(user.getUuid());
            System.out.println("유저 ID로 찾기: " + foundUserById);
        } catch (NotFoundException e) {
            System.out.println("유저 ID로 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 모든 유저 찾기 테스트
        try {
            userService.addUser(new User("anotherUser"));
            userService.addUser(new User("thirdUser"));
            userService.addUser(new User("fourthUser"));
            System.out.println("모든 유저 찾기:");
            userService.findAllUsers().forEach(u -> System.out.println("유저: " + u));
        } catch (NotFoundException e) {
            System.out.println("모든 유저 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 ID로 찾기 테스트
        try {
            User foundUserById = userService.findUserById(user.getUuid());
            System.out.println("유저 ID로 찾기 성공: " + foundUserById);
        } catch (NotFoundException e) {
            System.out.println("유저 단일 조회 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 이름으로 찾기 테스트
        try {
            User foundUserByName = userService.findUserByName("testUser");
            System.out.println("유저 이름으로 찾기: " + foundUserByName);
        } catch (NotFoundException e) {
            System.out.println("유저 이름으로 찾기 에러(사용자가 없음): " + e.getMessage());
        }

        // 유저 업데이트 테스트
        try {
            userService.updateUser("testUser", "updatedUser");
            User updatedUser = userService.findUserByName("updatedUser");
            System.out.println("유저 업데이트: " + updatedUser);
        } catch (NotFoundException e) {
            System.out.println("유저 업데이트 에러(사용자가 없음): " + e.getMessage());
        } catch (DuplicateException e) {
            System.out.println("유저 업데이트 에러(중복된 사용자 이름): " + e.getMessage());
        }

        // 유저 삭제 테스트
        try {
            userService.removeUser(user);
            System.out.println("유저 삭제 성공: " + user);
        } catch (NotFoundException e) {
            System.out.println("유저 삭제 에러(사용자가 없음): " + e.getMessage());
        }

    }

    private static void testCaseFileChannel() {
        System.out.println("Testing Channel functionality...");
        ChannelService channelService = new FileChannelInterface();
        UUID id = UUID.randomUUID();
        Channel channel = new Channel("testChannel", "This is a test channel");
        // 채널 추가 테스트
        try {
            channelService.addChannel(channel);
            System.out.println("채널 추가 성공: " + channel);
        } catch (DuplicateException e) {
            System.out.println("채널 추가 에러(중복된 채널 이름): " + e.getMessage());
        }

        // 채널 추가 예외 사항(중복된 채널 이름)
        try {
            channelService.addChannel(new Channel("testChannel", "This is a test channel"));
        } catch (DuplicateException e) {
            System.out.println("채널 추가 중복 에러(채널 이름 중복): " + e.getMessage());
        }

        // 채널 이름으로 찾기 테스트
        try {
            Channel foundChannel = channelService.findChannelByName("testChannel");
            System.out.println("채널 찾기 성공: " + foundChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 찾기 에러(채널이 없음): " + e.getMessage());
        }

        // 모든 채널 찾기 테스트
        try {
            channelService.addChannel(new Channel("anotherChannel", "This is another channel"));
            channelService.addChannel(new Channel("thirdChannel", "This is the third channel"));
            System.out.println("모든 채널 찾기:");
            channelService.findAllChannels().forEach(c -> System.out.println("채널: " + c));
        } catch (NotFoundException e) {
            System.out.println("모든 채널 찾기 에러(채널이 없음): " + e.getMessage());
        }

        // 채널 업데이트 테스트
        try {
            channelService.updateChannel(channel.getChannelName(), "updatedChannel");
            Channel updatedChannel = channelService.findChannelByName("updatedChannel");
            System.out.println("채널 업데이트 성공: " + updatedChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 업데이트 에러(채널이 없음): " + e.getMessage());
        } catch (DuplicateException e) {
            System.out.println("채널 업데이트 에러(중복된 채널 이름): " + e.getMessage());
        }

        // 채널 설명 업데이트 테스트
        try {
            channelService.updateChannelDescription(channel.getChannelName(), "This is an updated channel description");
            Channel updatedChannel = channelService.findChannelById(id);
            System.out.println("채널 설명 업데이트 성공: " + updatedChannel);
        } catch (NotFoundException e) {
            System.out.println("채널 설명 업데이트 에러(채널이 없음): " + e.getMessage());
        }
        // 채널 삭제 테스트
        try {
            channelService.removeChannel(channel);
            System.out.println("채널 삭제 성공: " + channel);
        } catch (NotFoundException e) {
            System.out.println("채널 삭제 에러(채널이 없음): " + e.getMessage());
        }
    }

    private static void testCaseFileMessage() {
        System.out.println("Testing Message functionality...");
        MessageService messageService = new FileMessageInterface();

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        String messageContent = "Hello, this is a test message!";
        Message message = new Message(messageContent, senderId, receiverId);
        // 메시지 추가 테스트
        try {
            messageService.addMessage(message);
            System.out.println("메시지 추가 성공: " + message);
        } catch (Exception e) {
            System.out.println("메시지 추가 에러: " + e.getMessage());
        }
        // 메세지 전체 찾기 테스트
        try {
            messageService.addMessage(new Message("Another test message", senderId, receiverId));
            messageService.addMessage(new Message("Third test message", senderId, receiverId));
            System.out.println("모든 메시지 찾기:");
            messageService.findAllMessages().forEach(m -> System.out.println("메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("모든 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 ID로 찾기 테스트
        try {
            Message foundMessage = messageService.findMessageById(message.getUuid());
            System.out.println("메시지 ID로 찾기 성공: " + foundMessage);
        } catch (NotFoundException e) {
            System.out.println("메시지 ID로 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 보낸 사람으로 찾기 테스트
        try {
            messageService.addMessage(new Message("Message from sender", senderId, receiverId));
            messageService.findMessagesBySender(senderId).forEach(m -> System.out.println("보낸 메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("보낸 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 받는 사람으로 찾기 테스트
        try {
            messageService.addMessage(new Message("Message to receiver", senderId, receiverId));
            messageService.findMessagesByReceiver(receiverId).forEach(m -> System.out.println("받은 메시지: " + m));
        } catch (NotFoundException e) {
            System.out.println("받은 메시지 찾기 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 업데이트 테스트
        try {
            String newMessageContent = "This is an updated message content.";
            messageService.updateMessage(message.getUuid(), newMessageContent);
            Message updatedMessage = messageService.findMessageById(message.getUuid());
            System.out.println("메시지 업데이트 성공: " + updatedMessage);
        } catch (NotFoundException e) {
            System.out.println("메시지 업데이트 에러(메시지가 없음): " + e.getMessage());
        }

        // 메시지 삭제 테스트
        try {
            messageService.removeMessage(message);
            System.out.println("메시지 삭제 성공: " + message);
        } catch (NotFoundException e) {
            System.out.println("메시지 삭제 에러(메시지가 없음): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("메시지 삭제 에러: " + e.getMessage());
        }
    }
}