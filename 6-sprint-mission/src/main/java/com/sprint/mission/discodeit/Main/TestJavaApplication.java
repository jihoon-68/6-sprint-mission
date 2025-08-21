package com.sprint.mission.discodeit.Main;

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


public class TestJavaApplication {

    public User testUserSenderTempRun() {
        UserService userService = JCFUserService.getInstance();
        List<User> userData = userService.allRead();

        User user = userService.create("이호건");
        System.out.println("유저 생성: " + user);
        User modifiedUserName = userService.modify(user.getCommon().getId());
        modifiedUserName.setName("이호건 이름 수정");
        System.out.println("유저 이름 수정: " + modifiedUserName);
        User deletedUser = userService.delete(user.getCommon().getId());
        userData.remove(deletedUser);
        System.out.println("이호건 유저 삭제 후 유저 조회: " + userData);

        User user1 = userService.create("첫번째 유저");
        User user2 = userService.create("두번째 유저");
        System.out.println("더미 유저 생성 후 모든 유저 조회: " + userData);

        return user;
    }

    public User testUserRecieverTempRun() {
        UserService userService = JCFUserService.getInstance();
        List<User> userData = userService.allRead();

        User user = userService.create("코드잇");
        System.out.println("유저 생성: " + user);
        User modifiedUserName = userService.modify(user.getCommon().getId());
        modifiedUserName.setName("코드잇 이름 수정");
        System.out.println("유저 이름 수정: " + modifiedUserName);
        User deletedUser = userService.delete(user.getCommon().getId());
        userData.remove(deletedUser);
        System.out.println("코드잇 유저 삭제 후 유저 조회: " + userData);

        return user;
    }

    public Channel testChannelTempRun(){
        ChannelService channelService = JCFChannelService.getInstance();
        List<Channel> channelData = channelService.allRead();

        Channel channel = channelService.create("백엔드 6기");
        System.out.println("채널 생성: " + channel);
        Channel modifiedChannelName = channelService.modify(channel.getCommon().getId());
        modifiedChannelName.setName("백엔드 6기 채널명 수정");
        System.out.println("채널명 수정: " + modifiedChannelName);
        Channel deletedChannel = channelService.delete(channel.getCommon().getId());
        channelData.remove(deletedChannel);
        System.out.println("삭제 후 채널명 조회: " + channelData);

        return channel;
    }

    public void testMessageTempRun(){
        MessageService messageService = JCFMessageService.getInstance();
        List<Message> messageData = messageService.allRead();
        TestJavaApplication testJavaApplication = new TestJavaApplication();

        Message message = messageService.create(testJavaApplication.testUserSenderTempRun(), testJavaApplication.testUserRecieverTempRun(),"안녕", testJavaApplication.testChannelTempRun());
        System.out.println("메시지 생성: " + message);
        Message modifiedMessage = messageService.modify(message.getCommon().getId());
        modifiedMessage.setContent("안녕하세요");
        System.out.println("메시지 내용 수정: " + modifiedMessage);
        Message deletedMessage = messageService.delete(message.getCommon().getId());
        messageData.remove(deletedMessage);
        System.out.println("삭제 후 메시지 조회: " + messageData);

    }

    public static void main(String[] args) {
        new TestJavaApplication().testMessageTempRun();
    }
}
