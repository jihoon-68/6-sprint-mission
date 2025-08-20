package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.service.jcf.*;
import com.sprint.mission.discodeit.entity.*;

import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        //서비스 객체 생성
        JCFUserService jcfUserService = new JCFUserService();
        JCFChannelService jcfChannelService = new JCFChannelService();
        JCFMessageService jcfMessageService = new JCFMessageService();

        // 등록

        // 사용자 등록
        jcfUserService.createUser("KCH");
        jcfUserService.createUser("PJS");

        //채널 등록
        jcfChannelService.createChannel("codeIt");
        jcfChannelService.createChannel("discodeIt");

        // 메시지 등록
        jcfMessageService.createMessage("Hello World");
        jcfMessageService.createMessage("This is a test message");

        // 단건 조회

        // 사용자 조회
        User user1 = jcfUserService.getUsers().get(0);
        System.out.println("등록된 사용자: " + user1.getName());

        // 메시지 조회
        Message message1 = jcfMessageService.getMessages().get(1);
        System.out.println("등록된 메세지: " + message1.getMsg());

        // 채널 조회
        Channel channel1 = jcfChannelService.getChannels().get(0);
        System.out.println("등록된 채널: " + channel1.getChName() + "\n");

        // 다건 조회

        // 모든 사용자 조회
        System.out.println("모든 사용자 조회: ");
        for(User user : jcfUserService.getUsers()){
            System.out.println(user.getName());
        }
        System.out.println();


        //모든 메시지 조회
        System.out.println("모든 메시지 조회: ");
        for(Message message : jcfMessageService.getMessages()){
            System.out.println(message.getMsg());
        }
        System.out.println();

        //모든 채널 조회
        System.out.println("모든 채널 조회: ");
        for(Channel channel : jcfChannelService.getChannels()){
            System.out.println(channel.getChName());
        }
        System.out.println();

        // 수정

        // 사용자 수정
        UUID user1Id = user1.getId();
        jcfUserService.updateUser(user1Id, "KKH");
        User updatedUser = jcfUserService.getUser(user1Id);
        System.out.println("수정된 사용자: " + updatedUser.getName());
        System.out.println("모든 사용자 조회: ");
        for(User user : jcfUserService.getUsers()){
            System.out.println(user.getName());
        }
        System.out.println();

        //채널 수정
        UUID channel1Id = channel1.getId();
        jcfChannelService.updateChannel(channel1Id, "Sprint");
        Channel updatedChannel = jcfChannelService.getChannel(channel1Id);
        System.out.println("수정된 채널: "   + updatedChannel.getChName());
        System.out.println("모든 채널 조회: ");
        for(Channel channel : jcfChannelService.getChannels()){
            System.out.println(channel.getChName());
        }
        System.out.println();

        // 메세지 수정
        UUID message1Id = message1.getId();
        jcfMessageService.updateMessage(message1Id, "Let's Sprint!");
        Message updatedMessage = jcfMessageService.getMessage(message1Id);
        System.out.println("수정된 메세지: " + updatedMessage.getMsg());
        System.out.println("모든 메세지 조회:");
        for(Message message : jcfMessageService.getMessages()){
            System.out.println(message.getMsg());
        }
        System.out.println();

        // 삭제
        jcfUserService.deleteUser(user1Id);
        jcfChannelService.deleteChannel(channel1Id);
        jcfMessageService.deleteMessage(message1Id);

        // 삭제된 데이터 조회

        // 삭제된 사용자 조회
        User deletedUser = jcfUserService.getUser(user1Id);
        if(deletedUser == null){
            System.out.println("사용자가 삭제 되었습니다.");
        } else {
            System.out.println("삭제 되지 않은 사용자: " + deletedUser.getName());
        }
        System.out.println("모든 사용자 조회: ");
        for(User user : jcfUserService.getUsers()){
            System.out.println(user.getName());
        }
        System.out.println();

        // 삭제된 채널 조회

        Channel deletedChannel = jcfChannelService.getChannel(channel1Id);
        if(deletedChannel == null){
            System.out.println("채널이 삭제 되었습니다.");
        } else {
            System.out.println("삭제 되지 않은 채널: " + deletedChannel.getChName());
        }
        System.out.println("모든 채널 조회: ");
        for(Channel channel : jcfChannelService.getChannels()){
            System.out.println(channel.getChName());
        }
        System.out.println();

        // 삭제된 메시지 조회

        Message deletedMessage = jcfMessageService.getMessage(message1Id);
        if(deletedMessage == null){
            System.out.println("메세지가 삭제 되었습니다.");
        } else {
            System.out.println("삭제 되지 않은 메시지: "  + deletedMessage.getMsg());
        }
        System.out.println("모든 메세지 조회: ");
        for(Message message : jcfMessageService.getMessages()){
            System.out.println(message.getMsg());
        }
    }
}
