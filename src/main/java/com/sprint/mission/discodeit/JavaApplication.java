package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ServiceFactory;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * User의 경우,
 * - 중복 이름 또는 이메일 throw
 * Message의 경우,
 * - 찾을 수 없는 사용자나 채널 throw
 */
public class JavaApplication {
    public static void main(String[] args) {
        System.out.println("=== [Sprint Mission] DisCodeIt ==========");

        UserService userService = ServiceFactory.getUserService();
        ChannelService channelService = ServiceFactory.getChannelService();
        MessageService messageService = ServiceFactory.getMessageService();


        // 1. User
        System.out.println("\n=== [Sprint Mission] User ==========");
        // Create
        User user1 = userService.create("admin", "123@123.co.kr", "adminpassword");
        System.out.println("Created User: " + user1.getUsername());

        User user2 = userService.create("formal", "11@11.g.k", "1212");
        System.out.println("Created User: " + user2.getUsername());
        // Create (duplicate)
        // User user3 = userService.create("formal", "11@11.g.k", "1212");

        // Read
        Optional<User> findUser = userService.findById(user1.getId());
        System.out.println("Find User: " + findUser.orElse(null));

        List<User> allUser = userService.findAll();
        System.out.println("All User count: " + allUser.size());
        // Update
        userService.update(user1.getId(), null, null, "newpassword");
        System.out.println("Updated User: " + user1.getUsername() + " " + user1.getEmail() + " " + user1.getPassword());
        // Delete
        userService.delete(user1.getId());
        System.out.println("Deleted User: " + userService.findById(user1.getId()).orElse(null));


        // 2. Channel
        System.out.println("\n\n=== [Sprint Mission] Channel ==========");
        // Create
        Channel channel1 = channelService.create("test1", "test channel1", Channel.ChannelType.TEXT);
        System.out.println("Created Channel: " + channel1.getTitle());

        Channel channel2 = channelService.create("test2", "test channel2", Channel.ChannelType.VOICE);
        System.out.println("Created Channel: " + channel2.getTitle());

        Channel channel3 = channelService.create("test3", "test channel3", Channel.ChannelType.TEXT);
        System.out.println("Created Channel: " + channel3.getTitle());
        // Read
        Optional<Channel> findChannel = channelService.findById(channel1.getId());
        System.out.println("Find Channel: " + findChannel.orElse(null));

        List<Channel> allChannel = channelService.findAll();
        System.out.println("All Channel count: " + allChannel.size());
        // Update
        channelService.update(channel1.getId(), "test1_update", "test channel1 update");
        System.out.println("Updated Channel: " + channel1.getTitle() + " " + channel1.getDescription() + " " + channel1.getType());
        // Delete
        channelService.delete(channel1.getId());
        System.out.println("Deleted Channel: " + channelService.findById(channel1.getId()).orElse(null));


        // 3. Message
        System.out.println("\n\n=== [Sprint Mission] Message ==========");
        // Create
        Message msg1 = messageService.create("test message1", user2, channel2, Message.MessageType.TEXT);
        System.out.println("Created Message: " + msg1.getContent());

        Message msg2 = messageService.create("test message2", user2, channel2, Message.MessageType.TEXT);
        System.out.println("Created Message: " + msg2.getContent());

        Message msg3 = messageService.create("test message3", user2, channel3, Message.MessageType.TEXT);
        System.out.println("Created Message: " + msg3.getContent());
        // Read
        Optional<Message> findMsg1 = messageService.findById(msg1.getId());
        System.out.println("Find Message: " + findMsg1.orElse(null));

        List<Message> allMsg = messageService.findAll();
        System.out.println("All Message count: " + allMsg.size());
        // Update
        messageService.update(msg1.getId(), "123123 Hello World");
        System.out.println("Updated Message: " + msg1.getContent());
        // Delete
        messageService.delete(msg1.getId());
        System.out.println("Deleted Message: " + messageService.findById(msg1.getId()).orElse(null));

    }
}
