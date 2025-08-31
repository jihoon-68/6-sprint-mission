package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.List;

public class JavaApplication {
    public static void main(String[] args) {
        //service
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();

        //user create
        User userA = new User("userA");
        User userB = new User("userB");
        userService.createUser(userA);
        userService.createUser(userB);
        System.out.println("createdUser : " + userA.getUserName());
        System.out.println("createdUser : " + userB.getUserName());
        System.out.println();

        //user read
        User foundUser = userService.readUser(userA.getuserId());
        if (foundUser != null) {
            System.out.println("readUser : " + foundUser.getUserName());
            System.out.println();
        }

        //user readAll
        List<User> users = userService.readAll();
        for (User user : users) {
            System.out.println("readAllUser : " + user.getUserName());
            System.out.println();
        }

        //user update
        userA.updateUser("updateUserA");
        userService.updateUser(userA.getuserId(), userA);
        User updatedUser = userService.readUser(userA.getuserId());
        if (updatedUser != null) {
            System.out.println("updatedUser : " + updatedUser.getUserName());
        }
        System.out.println();

        //user delete
        userService.deleteUser(userB.getuserId());
        System.out.println("after delete");
        for (User u : userService.readAll()) {
            System.out.println("readAllUser : " + u.getUserName());
        }

        //channel create
        Channel channelA = new Channel("channelA", userA.getuserId());
        Channel channelB = new Channel("channelB", userA.getuserId());
        channelService.create(channelA);
        channelService.create(channelB);

        //channel read
        Channel foundChannel = channelService.read(channelA.getChannelId());
        if (foundChannel != null) {
            System.out.println("foundChannel : " + foundChannel.getChannelName());
        }

        //channel readAll
        List<Channel> channels = channelService.readAll();
        for (Channel c : channels) {
            System.out.println("readAllChannel : " + c.getChannelName());
            System.out.println();
        }

        //channel update
        channelA.updateChannel("updatedChannelA");
        Channel updatedChannel = channelService.update(channelA.getOwnerId(), channelA);
        if (updatedChannel != null) {
            System.out.println("updatedChannel : " + updatedChannel.getChannelName());
        }
        System.out.println();

        //channel delete
        channelService.delete(channelB.getChannelId());
        System.out.println("after delete");
        for (Channel c : channelService.readAll()) {
            System.out.println("readAllUser : " + c.getChannelName());
            System.out.println();
        }

        //message create
        Message messageA = new Message("messageA",userA.getuserId(),channelA.getChannelId());
        Message messageB = new Message("messageB",userA.getuserId(),channelA.getChannelId());
        messageService.create(messageA);
        messageService.create(messageB);

        //message read
        Message foundMessage = messageService.read(messageA.getmessageId());
        if (foundMessage != null) {
            System.out.println("foundMessage : " + foundMessage.getContent());
            System.out.println();
        }

        //message readAll
        List<Message> messages = messageService.readAll();
        for (Message m : messages) {
            System.out.println("messageReadAll : " + m.getContent());
            System.out.println();
        }

        //message update
        messageA.updateMessage("updatedMessageA");
        Message updatedMessage = messageService.update(messageA.getmessageId(), messageA);
        if (updatedMessage != null) {
            System.out.println("updatedMessage : " + updatedMessage.getContent());
        }
        System.out.println();

        //message delete
        messageService.delete(messageB.getmessageId());
        System.out.println("after delete message");
        for (Message m : messageService.readAll()) {
            System.out.println("readAllMessage : " + m.getContent());
            System.out.println();
        }



    }






}
