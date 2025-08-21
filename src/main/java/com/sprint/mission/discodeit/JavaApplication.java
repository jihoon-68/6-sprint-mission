package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.serviece.file.FileChannelService;
import com.sprint.mission.discodeit.serviece.file.FileMessageService;
import com.sprint.mission.discodeit.serviece.file.FileUserService;
import com.sprint.mission.discodeit.serviece.jcf.JCFChannelServiece;
import com.sprint.mission.discodeit.serviece.jcf.JCFMessageServiece;
import com.sprint.mission.discodeit.serviece.jcf.JCFUserServiece;

import java.util.UUID;

public class JavaApplication {
  public static void main(String[] args) {

    /// User
    /*JCFUserServiece userServiece = new JCFUserServiece();*/
      FileUserService fileUserService = new FileUserService();



    System.out.println("====add User====");
    User user1 = new User("SeoGyeongwon", "WonGyeong", "1234567889a");
    User user2 = new User("HueJackMan","JhonWuq","202402121");

/*
    userServiece.createUser(user1);
    userServiece.createUser(user2);
*/

      fileUserService.createUser(user1);
      fileUserService.createUser(user2);

    System.out.println();

    System.out.println("====find all User====");

    /*userServiece.readAllInfo().forEach(u -> System.out.println(u));*/
      fileUserService.readAllInfo().forEach(System.out::println);
    /// 하나의 유저만 조회

    System.out.println();

    System.out.println("====find one User====");
    UUID userId = user1.getUserId();
/*
    User foundUser = userServiece.readUser(userId);
*/
      User foundUser = fileUserService.readUser(userId);
    System.out.println(foundUser);

    System.out.println();
    //System.out.println("update user");

    /// 닉네임 수정
    System.out.println("===updated user====");
    /*foundUser.updateNickName("Suwon-Bro");
    userServiece.updateUser(foundUser);*/

    ///  비밀번호 수정
    System.out.println("update pwd");
    foundUser.updatePassWord("20000601");
    /*userServiece.updateUser(foundUser);*/
      fileUserService.updateUser(foundUser);

/*
    User updatedUser = userServiece.readUser(userId);
*/
      User updatedUser = fileUserService.readUser(userId);

    System.out.println(updatedUser);

    System.out.println();

    /// 유저 삭제
    /*System.out.println("====delete user====");
    userServiece.deleteUser(userId);*/
    ////삭제 후 조회
    /*User deletedUser = userServiece.readUser(userId);
    if(deletedUser == null){
      System.out.println("user has been deleted");
    }
    userServiece.readAllInfo().forEach(u -> System.out.println(u));*/


      /// Channel
/*
    JCFChannelServiece channelServiece = new JCFChannelServiece();
*/
      FileChannelService fileChannelService = new FileChannelService();
    /// 채널 추가
    System.out.println("====add Chanel====");

    Channel channel1 = new Channel("LA Hot men");
    Channel channel2 = new Channel("gotohome");

    /*channelServiece.createChannel(channel1);
    channelServiece.createChannel(channel2);*/
      fileChannelService.createChannel(channel1);
      fileChannelService.createChannel(channel2);

    System.out.println();

    ///채널 조회

    System.out.println("====find all channel====");
    /*channelServiece.readAllInfo().forEach(ch -> System.out.println(ch));*/
      fileChannelService.readAllInfo().forEach(System.out::println);

    System.out.println();

    System.out.println("====find one Channel====");
    UUID channelId = channel1.getChannelId();
    /*Channel foundChannel = channelServiece.readChannel(channelId);*/
      Channel foundChannel = fileChannelService.readChannel(channelId);
    System.out.println(foundChannel);

    /// 채널 수정
    System.out.println("====update Chanel===="); ///업데이트 샤넬~
    foundChannel.updateChannelName("Taecho Legacy");
//    channelServiece.updateChannel(foundChannel);
      fileChannelService.updateChannel(foundChannel);
    System.out.println(foundChannel);

    System.out.println();

    /// 채널 삭제
   /* System.out.println("====delete Channel====");

    channelServiece.deleteChannel(channelId);
    Channel deletedChannel = channelServiece.readChannel(channelId);
    if(deletedChannel == null){
      System.out.println("this channel has been deleted");
    }
    channelServiece.readAllInfo().forEach(ch-> System.out.println(ch));
*/


    /// 메시지
/*
    JCFMessageServiece messageServiece = new JCFMessageServiece(userService,channelServiece);
*/

      FileMessageService fileMessageService = new FileMessageService(fileUserService,fileChannelService);
    ///  메시지 추가
    Message message1 = new Message("Hello World");
    Message message2 = new Message("GoodBye World");

    message1.setUser(user1);
    message1.setChannel(channel1);

    message2.setUser(user2);
    message2.setChannel(channel2);

    /*messageServiece.createMessage(message2,user2.getUserId(),channel2.getChannelId());
      messageServiece.createMessage(message1,user1.getUserId(),channel1.getChannelId());*/
      fileMessageService.createMessage(message1,user1.getUserId(),channel1.getChannelId());
      fileMessageService.createMessage(message2, user2.getUserId(),channel2.getChannelId());

    System.out.println(" ====Create Message==== ");

    System.out.println("====read all Massage====");
/*
    messageServiece.readAllMessage().forEach(m -> System.out.println(m));
*/
      fileMessageService.readAllMessage().forEach(System.out::println);
    System.out.println();

    System.out.println(" ====read one Message==== ");
    UUID messageId = message1.getMessageId();
/*
    Message foundMessage = messageServiece.readMessage(messageId);
*/
      Message foundMessage = fileMessageService.readMessage(messageId);
    System.out.println(foundMessage);

    System.out.println();
    /// 메시지 수정
    System.out.println("====update Message====");

    foundMessage.updateText("Hello World is different Hi word?");
    /*messageServiece.updateMessage(foundMessage);*/
      fileMessageService.updateMessage(foundMessage);

    /*Message updatedMessage = messageServiece.readMessage(messageId);*/
      Message updatedMessage = fileMessageService.readMessage(messageId);
    System.out.println(updatedMessage);
    System.out.println();

    /// 메시지 삭제
    System.out.println("====delete message=====");
/*
    messageServiece.deleteMessage(messageId);
*/
    fileMessageService.deleteMessage(messageId);

    /// 삭제 후 조회
    /*Message deletedMessage = messageServiece.readMessage(messageId);*/
      Message deletedMessage = fileMessageService.readMessage(messageId);
    if(deletedMessage == null){
      System.out.println("Message couldn't find(it may be deleted");
    }
    /*messageServiece.readAllMessage().forEach(m -> System.out.println(m));*/
      fileMessageService.readAllMessage().forEach(System.out::println);
    System.out.println();


  }
}
