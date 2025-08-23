package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.serviece.file.FileChannelService;
import com.sprint.mission.discodeit.serviece.file.FileMessageService;
import com.sprint.mission.discodeit.serviece.file.FileUserService;
import com.sprint.mission.discodeit.serviece.jcf.JCFChannelServiece;
import com.sprint.mission.discodeit.serviece.jcf.JCFMessageServiece;
import com.sprint.mission.discodeit.serviece.jcf.JCFUserServiece;

import java.util.UUID;

public class JavaApplication {

    /// JCF
    //private static final JCFUserServiece userService = new JCFUserServiece();
    //private static final JCFChannelServiece channelServiece = new JCFChannelServiece();
    //private static final JCFMessageServiece messageServiece = new JCFMessageServiece(userService,channelServiece);

    ///  File Service
    //private static final FileUserService fileUserService = new FileUserService();
    //private static final FileChannelService fileChannelService = new FileChannelService();
    //private static final FileMessageService fileMessageService = new FileMessageService(fileUserService,fileChannelService);

    /// Repository
    //private static final JCFUserRepository jcfUserRepository = new JCFUserRepository();
    //private static final JCFChannelRepository jcfChannelRepository = new JCFChannelRepository();
    //private static final JCFMessageRepository jcfMessageRepository = new JCFMessageRepository(jcfUserRepository,jcfChannelRepository);

    /// File Repository
    private static final FileUserRepository fileUserRepository = new FileUserRepository();
    private static final FileChannelRepository fileChannelRepository = new FileChannelRepository();
    private static final FileMessageRepository fileMessageRepository = new FileMessageRepository(fileUserRepository,fileChannelRepository);


    public static void main(String[] args) {

        /////////////////////////////////////////////////////////////////////////////////////

        /// 사용자 기능 테스트
        System.out.println("================Start User testing==============\n");
        ///사용자 입력
        User user1 = addUser("SeoGyeongwon", "WonGyeong", "1234567889a");
        User user2 = addUser("HueJackMan", "JhonWuq", "202402121");
        User user3 = addUser("discodeit", "OungwuYen", "20233333");

        /// 전체 사용자 조회
        readAllUser();

        ///  특정 사용자 조회
        readUser(user1.getUserId());

        /// 특정 사용자 정보 수정 (user1)
        updateUserPassword(user1, "20000601");

        ///  특정 사용자 조회
        readUser(user1.getUserId());

        /// 특정 사용자 정보 수정 (user2)
        updateUserNickName(user2, "SamSSong_SDS");

        ///  특정 사용자 조회
        readUser(user2.getUserId());

        ///  특성 사용자 삭제
        deleteUser(user3.getUserId());

        ///  사용자 삭제 후 전체 조회
        readAllUser();

        //////////////////////////////////////////////////////////////////////

        /// Channel 기능 테스트
        System.out.println("================Start Channel testing==============\n");
        /// 채널 추가
        Channel channel1 = addChannel("LA Hot men");
        Channel channel2 = addChannel("go-to-home");

        ///  전체 채널 조회
        readAllChannel();

        /// 특정 채널 명 변경
        updateChannelName(channel1, "day-month-year");

        /// 특정 채널 조회
        readChannel(channel1.getChannelId());

        /// 채널 삭제
        deleteChannel(channel2.getChannelId());

        /// 채널 삭제 후 조회
        readAllChannel();

        /// /////////////////////////////////////////////////////////////////

        /// 메시지 기능 테스트
        System.out.println("================Start Message testing==============\n");
        ///  메시지 추가
        Message message1 = addMessage("Hello World", user1.getUserId(), channel1.getChannelId());
        Message message2 = addMessage("GoodBye World", user2.getUserId(), channel1.getChannelId());

        /// 전체 메시지 조회
        readAllMessages();

        /// 특정 메시지 수정
        updateMessage(message2, "I'll be back");

        ///  특정 메시지 조회
        readMessage(message2.getMessageId());

        ///  특정 메시지 삭제
        deleteMessage(message1.getMessageId());

        ///  메시지 삭제 후 조회
        readAllMessages();
    }
    /// User 기능
    private static User addUser(String userName, String nickName, String password){
        System.out.println("Now doing add User----------------->");
        User user = new User(userName, nickName,password);
        fileUserRepository.addUser(user);
        return user;
    }

    private static void readUser(UUID userId){
        User user = fileUserRepository.readUser(userId);
        System.out.println("+++++++++++++++Read Specific users+++++++++++++++");
        System.out.println(user);;
        System.out.println();
    }

    private static void readAllUser(){
        System.out.println("+++++++++++++++Read all users+++++++++++++++");
        fileUserRepository.readAllUser().forEach(System.out::println);
        System.out.println();
    }

    private static void updateUserPassword(User user,String newPassword){
        System.out.println("Now doing update User password-------------->");
        user.updatePassWord(newPassword);
        fileUserRepository.updateUser(user);
    }

    private static void updateUserNickName(User user,String newNickName){
        System.out.println("Now doing update User nickName-------------->");
        user.updateNickName(newNickName);
        fileUserRepository.addUser(user);
    }

    private static void deleteUser(UUID userId){
        System.out.println("Now doing delete User--------------->");
        fileUserRepository.deleteUser(userId);
    }

    /// Channle 기능
    private static Channel addChannel(String channelName){
        System.out.println("Now doing add Channel--------------->");
        Channel channel = new Channel(channelName);
        fileChannelRepository.addChannel(channel);
        return channel;
    }

    private static void readChannel(UUID channelId){
        Channel chanel = fileChannelRepository.readChannel(channelId);
        System.out.println("+++++++++++++++Read Specific Channel+++++++++++++++");
        System.out.println(chanel);
        System.out.println();
    }

    private static void readAllChannel(){
        System.out.println("+++++++++++++++Read all channels+++++++++++++++");
        fileChannelRepository.readAllChannel().forEach(System.out::println);
        System.out.println();
    }

    private static void updateChannelName(Channel channel,String newChannelName){
        System.out.println("Now doing update ChannelName-------------->");
       channel.updateChannelName(newChannelName);
        fileChannelRepository.updateChannel(channel);
    }

    private static void deleteChannel(UUID channelId){
        System.out.println("Now doing delete Channel--------------->");
        fileChannelRepository.deleteChannel(channelId);
    }

    ///  Message 기능
    private static Message addMessage(String text, UUID userId, UUID channelId){
        System.out.println("Now doing add Message--------------->");
        Message message = new Message(text);
        fileMessageRepository.addMessage(message,userId,channelId);
        return message;
    }

    private static void readMessage(UUID messageId){
        Message message = fileMessageRepository.readMessage(messageId);
        System.out.println("+++++++++++++++Read Specific massage!+++++++++++++++");
        System.out.println(message);
        System.out.println();
    }

    private static void readAllMessages(){
        System.out.println("+++++++++++++++Read all massage~+++++++++++++++");
        fileMessageRepository.readAllMessage().forEach(System.out::println);
        System.out.println();
    }

    private static void updateMessage(Message message, String newText){
        System.out.println("Now doing update Message--------------->");
        message.updateText(newText);
        fileMessageRepository.updateMessage(message);
    }

    private static void deleteMessage(UUID messageId){
        System.out.println("Now doing delete Message--------------->");
        fileMessageRepository.deleteMessage(messageId);
    }
}
