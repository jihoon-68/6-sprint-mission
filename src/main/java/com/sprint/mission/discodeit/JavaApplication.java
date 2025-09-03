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
import com.sprint.mission.discodeit.service.basic.file.BasicFileChannelService;
import com.sprint.mission.discodeit.service.basic.file.BasicFileMessageService;
import com.sprint.mission.discodeit.service.basic.file.BasicFileMessageService;
import com.sprint.mission.discodeit.service.basic.file.BasicFileUserService;
import com.sprint.mission.discodeit.service.basic.jcf.BasicJCFChannelService;
import com.sprint.mission.discodeit.service.basic.jcf.BasicJCFMessageService;
import com.sprint.mission.discodeit.service.basic.jcf.BasicJCFUserService;

import java.util.List;
import java.util.Scanner;

public class JavaApplication {
    public static void main(String[] args) {
        jcfTest();
        // fileTest();
    }

    public static void jcfTest(){
        JCFUserRepository userRepository = new JCFUserRepository();
        JCFChannelRepository channelRepository = new JCFChannelRepository();
        JCFMessageRepository messageRepository = new JCFMessageRepository();

        BasicJCFUserService userService = new BasicJCFUserService(userRepository);
        BasicJCFChannelService channelService = new BasicJCFChannelService(channelRepository);
        BasicJCFMessageService messageService = new BasicJCFMessageService(messageRepository);

        Scanner sc = new Scanner(System.in);
        boolean status = true;
        int num;

        while (status) {
            System.out.println("테스트할 서비스를 선택해주세요.");
            System.out.println("1. User, 2. Channel, 3. Message 4. 프로그램 종료");
            System.out.print("선택: ");
            num = sc.nextInt();
            sc.nextLine();

            switch (num){
                case 1:
                    // 유저 생성
                    User u1 = userService.createUser("ex@ex.com", "갈매기", "1234");
                    User u2 = userService.createUser("ex2@ex2.com", "참새", "5678");

                    // 조회 단건
                    System.out.print("유저 단건 조회 : "); // userName이 모두 독립적이라 가정.
                    System.out.println(userRepository.findByUserName("참새").toString()); /// 널 반환

                    // 조회 다건
                    System.out.println("모든 유저 조회 : ");
                    List<User> users = userRepository.findAll();
                    if (!users.isEmpty()) {
                        for (User user : users) {
                            System.out.println(user.toString());
                        }
                    }

                    // 정보 수정 및 수정 확인
                    userService.updatePassword(u2, "5678", "9999");
                    System.out.println("수정 확인 :");
                    userRepository.findByUserName("참새");

                    // 삭제 및 삭제 확인
                    userService.deleteUser(u1, "1234");
                    System.out.println("삭제 확인 :");
                    userRepository.findAll();
                    break;

                case 2:
                    // 등록
                    User u3 = userService.createUser("ex3@ex3.com", "까치", "0000");
                    Channel ch1 = channelService.createChannel("잡담방", u3);
                    Channel ch2 = channelService.createChannel("공지방", u3);

                    // 조회 단건
                    System.out.println("채널 단건 조회");
                    System.out.println(channelRepository.findByChannelName("잡담방").toString());

                    // 조회 다건
                    System.out.println("모든 채널 조회 : ");
                    List<Channel> channels = channelRepository.findAll();
                    if (!channels.isEmpty()) {
                        for (Channel channel : channels) {
                            System.out.println(channel.toString());
                        }
                    }

                    // 정보 수정 및 확인
                    channelService.renameChannel(u3, ch2, "공지공지방");
                    System.out.println("수정 확인 :");
                    System.out.println(channelRepository.findByChannelName("공지공지방").toString());

                    // 삭제 및 확인
                    channelService.deleteChannel(u3, ch1);

                    System.out.println("삭제 확인하기 :");
                    List<Channel> channels2 = channelRepository.findAll();
                    if (!channels2.isEmpty()) {
                        for (Channel channel : channels2) {
                            System.out.println(channel.toString());
                        }
                    }
                    break;

                case 3:
                    // 메시지 생성
                    User u4 = userService.createUser("ex4@ex4.com", "갈매기", "0000");
                    Channel c3 = channelService.createChannel("아무거나방", u4);
                    Message m1 = messageService.createMessage(u4, c3, "안녕하세요");

                    // 메시지 단건 조회
                    // System.out.println(m1.getChannel());
                    messageRepository.findMessageById("95d5b14b-a5fb-45a7-904b-0a27e25004fe");

                    // 채널별 메시지 조회
                    List<Message> messages1 = messageRepository.findMessagesByChannel(c3);
                    for (Message message : messages1) {
                        System.out.println(message.toString());
                    }

                    // 메시지 수정 및 확인
                    messageService.editMessage(u4, m1, "안녕히계세요");
                    messageRepository.findMessagesByChannel(c3);

                    // 삭제 및 확인
                    messageService.deleteMessage(u4, m1);
                    List<Message> messages2 = messageRepository.findMessagesByChannel(c3);
                    if (!messages2.isEmpty()){
                        for (Message message : messages2){
                            System.out.println(message.toString());
                        }
                    }
                    break;

                case 4:
                    System.out.println("종료합니다.");
                    status = false;
                    break;

                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
        sc.close();
    }

    public static void fileTest(){

        BasicFileUserService userService = new BasicFileUserService();
        BasicFileChannelService channelService = new BasicFileChannelService();
        BasicFileMessageService messageService = new BasicFileMessageService();

        FileUserRepository userRepository = new FileUserRepository();
        FileChannelRepository channelRepository = new FileChannelRepository();
        FileMessageRepository messageRepository = new FileMessageRepository();

        userRepository.clear();
        channelRepository.clear();
        messageRepository.clear(); ///

        Scanner sc = new Scanner(System.in);
        boolean status = true;
        int num;

        while (status) {
            System.out.println("테스트할 서비스를 선택해주세요.");
            System.out.println("1. User, 2. Channel, 3. Message 4. 프로그램 종료");
            System.out.print("선택: ");
            num = sc.nextInt();
            sc.nextLine();

            switch (num){
                case 1:
                    // 등록
                    User u1 = userService.createUser("ex@ex.com", "갈매기", "1234");
                    User u2 = userService.createUser("ex2@ex2.com", "참새", "5678");

                    // 조회 단건
                    System.out.println("유저 단건 조회 : "); // userName이 모두 독립적이라 가정.
                    System.out.println(userRepository.findByUserName("갈매기").toString());

                    // 조회 다건
                    System.out.println("모든 유저 조회 : ");
                    List<User> users = userRepository.findAll();
                    if (!users.isEmpty()) {
                        for (User user : users) {
                            System.out.println(user.toString());
                        }
                    }

                    // 정보 수정 및 수정 확인
                    userService.updatePassword(u2, "5678", "9999");
                    System.out.println("수정 확인 :");
                    userRepository.findByUserName("참새");

                    // 삭제 및 삭제 확인
                    userService.deleteUser(u1, "1234");
                    System.out.println("삭제 확인 :");
                    userRepository.findAll();
                    break;

                case 2:
                    // 등록
                    User u3 = userService.createUser("ex3@ex3.com", "까치", "0000");
                    Channel ch1 = channelService.createChannel("잡담방", u3);
                    Channel ch2 = channelService.createChannel("공지방", u3);

                    // 조회 단건
                    System.out.println("채널 단건 조회");
                    System.out.println(channelRepository.findByChannelName("잡담방").toString());
                    // 조회 다건
                    System.out.println("모든 채널 조회 : ");
                    List<Channel> channels = channelRepository.findAll();
                    if (!channels.isEmpty()) {
                        for (Channel channel : channels) {
                            System.out.println(channel.toString());
                        }
                    }

                    // 정보 수정 및 확인
                    channelService.renameChannel(u3, ch2, "공지공지방");
                    System.out.println("수정 확인 :");
                    System.out.println(channelRepository.findByChannelName("공지공지방").toString());

                    // 삭제 및 확인
                    channelService.deleteChannel(u3, ch1);

                    System.out.println("삭제 확인하기 :");
                    List<Channel> channels2 = channelRepository.findAll();
                    if (!channels2.isEmpty()) {
                        for (Channel channel : channels2) {
                            System.out.println(channel.toString());
                        }
                    }
                    break;

                case 3:
                    // 메시지 생성
                    User u4 = userService.createUser("ex4@ex4.com", "갈매기", "0000");
                    Channel c3 = channelService.createChannel("아무거나방", u4);
                    Message m1 = messageService.createMessage(u4, c3, "안녕하세요");

                    // 메시지 단건 조회
                    // System.out.println("메시지 단건 조회");
                    // System.out.println(m1.getChannel());
                    // messageService.findMessageById("95d5b14b-a5fb-45a7-904b-0a27e25004fe");

                    // 채널별 메시지 조회
                    List<Message> messages1 = messageRepository.findMessagesByChannel(c3);
                    if (!messages1.isEmpty()){
                        for (Message message : messages1){
                            System.out.println(message.toString());
                        }
                    }

                    // 메시지 수정 및 확인
                    messageService.editMessage(u4, m1, "안녕히계세요");
                    messageRepository.findMessagesByChannel(c3);

                    // 삭제 및 확인
                    messageService.deleteMessage(u4, m1);
                    List<Message> messages2 = messageRepository.findMessagesByChannel(c3);
                    if (!messages2.isEmpty()){
                        for (Message message : messages2){
                            System.out.println(message.toString());
                        }
                    }
                    else System.out.println("메시지 없음");
                    break;

                case 4:
                    System.out.println("종료합니다.");
                    status = false;
                    break;

                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
        sc.close();
    }
}
