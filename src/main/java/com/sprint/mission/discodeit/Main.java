package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class Main {
    public static void main(String[] args) {
        TestMessage(new FileMessageRepository());
        TestUser(new FileUserRepository());
        TestChannel(new FileChannelRepository());

//        TestMessage(new JCFMessageRepository());
//        TestUser(new JCFUserRepository());
//        TestChannel(new JCFChannelRepository());
    }

    private static void TestMessage(MessageRepository messageRepository)
    {
        System.out.println("======Testing Messages======");
        JCFMessageService message = new JCFMessageService(messageRepository);
        User u1 = new User("park","park mail");
        User u2 = new User("kim","kim mail");
        Channel channel = new Channel("TestChannel");

        message.CreateMessage(u2,u1,"hello");
        message.CreateMessage(u1,u2,"hello too");

        System.out.println("\n Select a message ");
        System.out.println(message.getMessage(u2));

        System.out.println("\n Select all messages ");
        message.getAllMessages().forEach(x -> System.out.println(x.toString()));

        Message msg = message.getMessage(u1);
        msg.updateMessage("nice to meet you");

        message.updateMessage(msg);
        System.out.println("\n update a message select all ");
        message.getAllMessages().forEach(x -> System.out.println(x.toString()));

        message.deleteMessage(msg);
        System.out.println("\n delete and Select all messages ");
        message.getAllMessages().forEach(x -> System.out.println(x.toString()));

        System.out.println("======Testing Messages Finished======");
        message.deleteAll();
    }

    private static void TestChannel(ChannelRepository channelRepository)
    {
        System.out.println("======Testing Channel======");
        JCFChannelService channel = new JCFChannelService(channelRepository);
        channel.createChannel("aa");
        channel.createChannel("bb");

        System.out.println("\n Select a channel ");
        System.out.println(channel.getChannel("aa").toString());

        System.out.println("\n Select all channels ");
        channel.getChannels().forEach(x-> System.out.println(x.toString()));

        Channel temp = channel.getChannel("aa");
        temp.updateChannelName("aa11");
        channel.updateChannel(temp);

        System.out.println("\n update and select channels ");
        channel.getChannels().forEach(x-> System.out.println(x.toString()));
        channel.deleteChannel(temp);
        System.out.println("\n delete and select channels ");
        channel.getChannels().forEach(x-> System.out.println(x.toString()));
        System.out.println("======Testing Channel Finished======");
        channel.deleteAll();
    }

    private static void TestUser(UserRepository userRepository)
    {
        System.out.println("======Testing User======");
        JCFUserService user = new JCFUserService(userRepository);
        user.createUser("park","test");
        user.createUser("kim","test11");

        System.out.println("\n Select a user ");
        System.out.println(user.getUser("test").toString());
        System.out.println("\n Select all users ");
        user.getUsers().forEach(x-> System.out.println(x.toString()));

        User temp = user.getUser("test");
        temp.updateUserName("parkpark");
        user.updateUser(temp);

        System.out.println("\n update and select all users ");
        user.getUsers().forEach(x-> System.out.println(x.toString()));
        user.deleteUser(temp);
        System.out.println("\n delete and select users ");
        user.getUsers().forEach(x-> System.out.println(x.toString()));
        System.out.println("======Testing User Finished======");
        user.deleteAll();
    }
}
