package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannel;
import com.sprint.mission.discodeit.service.jcf.JCFMessage;
import com.sprint.mission.discodeit.service.jcf.JCFUser;


public class Main {
    public static void main(String[] args) {
        JCFChannel jcfChannel = new JCFChannel();
        JCFMessage jcfMessage = new JCFMessage();
        JCFUser jcfUser = new JCFUser();


        //유저 등록
        System.out.println("================유저 등록=================");
        jcfUser.createUser("kim",20,"kim01@codeit.com");
        jcfUser.createUser("lee",21,"lee02@codeit.com");
        jcfUser.createUser("park",22,"park03@codeit.com");
        jcfUser.createUser("namgung",23,"namgung04@codeit.com");
        jcfUser.createUser("bae",24,"bae05@codeit.com");

        System.out.println("유저 등록: kim,20,kim01@codeit.com");
        System.out.println("유저 등록: lee,21,lee02@codeit.com");
        System.out.println("유저 등록: park,22,kim01@codeit.com");
        System.out.println("유저 등록: namgung,23,namgung04@codeit.com");
        System.out.println("유저 등록: bae,24,bae05@codeit.com");


        //유저 조회(단건,다건)
        System.out.println("================유저 조회(단건,다건)=================");
        User kimUser = jcfUser.findUserByUserEmail("kim01@codeit.com");
        System.out.println(kimUser);
        System.out.println(jcfUser.findAllUsers());

        //유저 수정
        System.out.println("================유저 수정=================");
        kimUser.updateEmail("kim011@codeit.com");
        jcfUser.updateUser(kimUser);
        System.out.println(jcfUser.findUserById(kimUser.getUserId()));

        //유저 삭제
        System.out.println("================유저 삭제=================");
        jcfUser.deleteUser(kimUser.getUserId());
        System.out.println(jcfUser.findAllUsers());

        System.out.println();

        //서버 생성을 위한 유저형의 유저변수 생성
        User leeUser = jcfUser.findUserByUserEmail("lee02@codeit.com");
        User parkUser = jcfUser.findUserByUserEmail("park03@codeit.com");
        User namgungUser = jcfUser.findUserByUserEmail("namgung04@codeit.com");
        User baeUser = jcfUser.findUserByUserEmail("bae05@codeit.com");


        //서버 등록
        System.out.println("================서버 등록=================");
        Channel channel1 = jcfChannel.createChannel("1팀",leeUser.getUsername());
        Channel channel2 =jcfChannel.createChannel("2팀",parkUser.getUsername());
        Channel channel3 =jcfChannel.createChannel("3팀",namgungUser.getUsername());
        Channel channel4 =jcfChannel.createChannel("4팀",baeUser.getUsername());

        System.out.println("서버 등록: 1팀,lee");
        System.out.println("서버 등록: 2팀,park");
        System.out.println("서버 등록: 3팀,namgung");
        System.out.println("서버 등록: 4팀,bae");

        //서버 조회(단건,다건)
        System.out.println("================서버 조회(단건,다건)=================");
        System.out.println(jcfChannel.findChannelById(channel1.getChannelId()));
        System.out.println(jcfChannel.findAllChannels());

        //서버 수정
        System.out.println("================서버 수정=================");
        channel1.updateChanelName("1조");
        jcfChannel.updateChannel(channel1);
        System.out.println(jcfChannel.findChannelById(channel1.getChannelId()));

        //서버 삭제
        System.out.println("================서버 삭제=================");
        jcfChannel.deleteChannel(channel3.getChannelId());
        System.out.println(jcfChannel.findAllChannels());

        System.out.println();


        System.out.println("================메시지 등록=================");
        System.out.println(jcfMessage.createMessage(leeUser.getUsername(),"등록 테스트 케이스 01"));
        System.out.println(jcfMessage.createMessage(parkUser.getUsername(),"등록 테스트 케이스 02"));
        System.out.println(jcfMessage.createMessage(namgungUser.getUsername(),"등록 테스트 케이스 03"));
        System.out.println(jcfMessage.createMessage(baeUser.getUsername(),"등록 테스트 케이스 04"));
        System.out.println(jcfMessage.createMessage(leeUser.getUsername(),"등록 테스트 케이스 05"));


        System.out.println("================메시지 조회(단건,다건)=================");
        Message newMessage = jcfMessage.createMessage(namgungUser.getUsername(),"등록 테스트 케이지06");
        System.out.println(jcfMessage.findMessageById(newMessage.getMessageId()));
        System.out.println(jcfMessage.findAllMessages());

        System.out.println("================메시지 수정=================");
        newMessage.updateMessage("수정 테스트 01");
        jcfMessage.updateMessage(newMessage);
        System.out.println(jcfMessage.findMessageById(newMessage.getMessageId()));

        System.out.println("================메시지 삭제=================");
        jcfMessage.deleteMessage(newMessage.getMessageId());
        System.out.println(jcfMessage.findAllMessages());






    }


}
