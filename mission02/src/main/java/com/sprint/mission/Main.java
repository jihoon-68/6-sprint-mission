package com.sprint.mission;


import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelInterface;
import com.sprint.mission.discodeit.service.MessageInterface;
import com.sprint.mission.discodeit.service.UserInterface;
import com.sprint.mission.discodeit.service.file.FileChannelInterface;
import com.sprint.mission.discodeit.service.file.FileMessageInterface;
import com.sprint.mission.discodeit.service.file.FileUserInterface;
import com.sprint.mission.discodeit.service.jcf.JCFChannelInterface;
import com.sprint.mission.discodeit.service.jcf.JCFMessageInterface;
import com.sprint.mission.discodeit.service.jcf.JCFUserInterface;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UserInterface jcfUserInterface = new JCFUserInterface();
        ChannelInterface jcfChannelInterface = new JCFChannelInterface();
        MessageInterface jcfMessageInterface = new JCFMessageInterface();
        UserInterface fileUserInterface = new FileUserInterface();
        ChannelInterface fileChannelInterface = new FileChannelInterface();
        MessageInterface fileMessageInterface = new FileMessageInterface();
        System.out.println("JCF 테스트 시작");
        testCreate(jcfMessageInterface, jcfUserInterface, jcfChannelInterface);
        testRead(jcfMessageInterface, jcfUserInterface, jcfChannelInterface);
        testUpdate(jcfMessageInterface, jcfUserInterface, jcfChannelInterface);
        testDelete(jcfMessageInterface, jcfUserInterface, jcfChannelInterface);
        System.out.println("JCF 테스트 종료");
        System.out.println("File 테스트 시작");
        testCreate(fileMessageInterface, fileUserInterface, fileChannelInterface);
        testRead(fileMessageInterface, fileUserInterface, fileChannelInterface);
        testUpdate(fileMessageInterface, fileUserInterface, fileChannelInterface);
        testDelete(fileMessageInterface, fileUserInterface, fileChannelInterface);
        System.out.println("File 테스트 종료");

    }

    private static void testCreate(MessageInterface messageInterface, UserInterface userInterface, ChannelInterface channelInterface) {
        try{
            channelInterface.create("테스트 채널", "테스트 채널입니다.");
            channelInterface.create("테스트 채널2", "테스트 채널입니다.");
            channelInterface.create("테스트 채널3", "테스트 채널입니다.");
            List<Channel> channels = channelInterface.listAllChannels();
            System.out.println("채널 목록: " + channels);
        } catch (DuplicateException e) {
            System.out.println("중복 채널 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            channelInterface.create("테스트 채널", "테스트 채널입니다.");
        } catch (DuplicateException e) {
            System.out.println("중복 채널 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try{
            userInterface.create("테스트 유저");
            userInterface.create("테스트 유저2");
            userInterface.create("테스트 유저3");
            List<User> users = userInterface.getUsers();
            System.out.println("유저 목록: " + users);
        } catch (DuplicateException e) {
            System.out.println("중복 유저 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            userInterface.create("테스트 유저");
        } catch (DuplicateException e) {
            System.out.println("중복 유저 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try{
            User user = userInterface.getUserByName("테스트 유저");
            System.out.println("메세지 발신 유저: " + user);
            userInterface.create("메세지 수신 유저");
            User receiver = userInterface.getUserByName("메세지 수신 유저");
            System.out.println("메세지 수신 유저: " + receiver);
            Channel channel = channelInterface.getChannelByName("테스트 채널");
            System.out.println("메세지 수신 채널: " + channel);
            messageInterface.send("테스트 메세지", user.getUuid(), receiver.getUuid());
            messageInterface.send("테스트 메세지2", user.getUuid(), channel.getUuid());
            messageInterface.send("테스트 메세지3", user.getUuid(), channel.getUuid());
            List<Message> messages = messageInterface.getMessagesByReceiver(receiver.getUuid());
            List<Message> messages2 = messageInterface.getMessagesByReceiver(channel.getUuid());
            System.out.println("개인 메세지함: " + messages);
            System.out.println("채널 메세지함: " + messages2);
        } catch (NotFoundException e) {
            System.out.println("메세지 생성 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testRead(MessageInterface messageInterface, UserInterface userInterface, ChannelInterface channelInterface) {
        try{
            User receiver = userInterface.getUserByName("메세지 수신 유저");
            Channel channel = channelInterface.getChannelByName("테스트 채널");
            List<Message> messages = messageInterface.getMessagesByReceiver(receiver.getUuid());
            List<Message> messages2 = messageInterface.getMessagesByReceiver(channel.getUuid());
            System.out.println("개인 메세지함: " + messages);
            System.out.println("채널 메세지함: " + messages2);
        } catch (NotFoundException e) {
            System.out.println("메세지 조회 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testUpdate(MessageInterface messageInterface, UserInterface userInterface, ChannelInterface channelInterface) {
        try{
            User user = userInterface.getUserByName("테스트 유저2");
            Channel channel = channelInterface.getChannelByName("테스트 채널2");
            userInterface.rename(user.getUuid(), "테스트 유저22");
            channelInterface.rename(channel.getUuid(), "테스트 채널22");
            user = userInterface.getUserByName("테스트 유저22");
            channel = channelInterface.getChannelByName("테스트 채널22");
            System.out.println("유저 이름 변경: " + user);
            System.out.println("채널 이름 변경: " + channel);
            messageInterface.send("수정 테스트 메세지", user.getUuid(), channel.getUuid());
            List<Message> messages = messageInterface.getMessagesByReceiver(channel.getUuid());
            Message message = messages.get(0);
            messageInterface.changeContext(message.getUuid(), "수정된 메세지 내용");
            messageInterface.getMessageById(message.getUuid());
            System.out.println("메세지 내용 변경: " + messageInterface.getMessageById(message.getUuid()));
        } catch (NotFoundException e) {
            System.out.println("메세지 수정 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testDelete(MessageInterface messageInterface, UserInterface userInterface, ChannelInterface channelInterface) {
        try{
            User user = userInterface.getUserByName("테스트 유저3");
            Channel channel = channelInterface.getChannelByName("테스트 채널3");
            userInterface.delete(user.getUuid());
            channelInterface.delete(channel.getUuid());
            System.out.println("유저 삭제 완료");
            System.out.println("채널 삭제 완료");
            messageInterface.send("삭제 테스트 메세지", user.getUuid(), channel.getUuid());
            List<Message> messages = messageInterface.getMessagesByReceiver(channel.getUuid());
            Message message = messages.get(0);
            messageInterface.delete(message.getUuid());
            System.out.println("메세지 삭제 완료");
            List<Message> messageList = messageInterface.getMessagesByReceiver(channel.getUuid());
        } catch (NotFoundException e) {
            System.out.println("메세지 삭제 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


}