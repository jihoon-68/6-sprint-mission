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
import java.util.Scanner;
import java.util.stream.Collectors;

public class JavaApplication {
    public static void main(String[] args) {
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();
        UserService userService = new JCFUserService();
        List<User> userList = userService.allRead();
        List<Message> messageList = messageService.allRead();
        List<Channel> channelList = channelService.allRead();

        Scanner sc = new Scanner(System.in);

        boolean run = true;
        while(run) {
            System.out.println("1. 유저 생성");
            System.out.println("2. 유저 관리");
            System.out.println("3. 메시지 보내기");
            System.out.println("4. 메시지 관리");
            System.out.println("5. 채널 생성");
            System.out.println("6. 채널 관리");
            System.out.println("7. 종료");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("유저 이름 입력");
                    String userName = sc.nextLine();
                    userService.create(userName);
                    break;
                case 2:

                    boolean runU = true;
                    while(runU){
                        System.out.println("1. 유저 조회");
                        System.out.println("2. 유저 리스트");
                        System.out.println("3. 유저 이름 수정");
                        System.out.println("4. 유저 삭제");
                        System.out.println("5. 메뉴 복귀");
                        int u_choice = sc.nextInt();
                        sc.nextLine();

                        switch (u_choice) {
                            case 1:
                                System.out.println("조회할 유저 이름: ");
                                String readUser = sc.nextLine();
                                userService.read(readUser);
                                break;
                            case 2:

                                System.out.println("유저 리스트: " + userList.stream().collect(Collectors.toList()));
                                break;
                            case 3:
                                System.out.println("수정할 유저 이름: ");
                                String modifyUser = sc.nextLine();
                                userService.modify(modifyUser);
                                break;
                            case 4:
                                System.out.println("삭제할 유저 이름: ");
                                String deleteUser = sc.nextLine();
                                userService.delete(deleteUser);
                                break;
                            case 5:
                                runU = false;
                                System.out.println("종료");
                                break;
                            default:
                                System.out.println("잘못된 입력");
                                break;
                        }
                    }
                    break;

                case 3:

                    System.out.println("보내는 사람: ");
                    String send = sc.nextLine();
                    User sender = userList.stream().filter(user->user.getName().equals(send)).findFirst().orElse(null);   ////
                    if(sender==null){
                        System.out.println("해당 유저가 없습니다");
                        break;
                    }

                    System.out.println("받는 사람: ");
                    String recieve = sc.nextLine();
                    User reciever = userList.stream().filter(user->user.getName().equals(recieve)).findFirst().orElse(null);  ////
                    if(reciever ==null){
                        System.out.println("해당 유저가 없습니다");
                        break;
                    }

                    System.out.println("메시지 내용: ");
                    String content = sc.nextLine();

                    System.out.println("메시지 보내는 채널명: ");
                    String messageInCh = sc.nextLine();
                    Channel messageInChannel = channelList.stream().filter(channel -> channel.getName().equals(messageInCh)).findFirst().orElse(null);
                    if(messageInChannel ==null){
                        System.out.println("해당 채널이 없습니다");
                        break;
                    }
                    messageService.create(sender, reciever, content, messageInChannel);
                    break;
                case 4:

                    boolean runMsg = true;
                    while(runMsg){
                        System.out.println("1. 메시지 조회");
                        System.out.println("2. 모든 메시지");
                        System.out.println("3. 메시지 수정");
                        System.out.println("4. 메시지 삭제");
                        System.out.println("5. 메뉴 복귀");
                        int msg_choice = sc.nextInt();
                        sc.nextLine();

                        switch (msg_choice) {
                            case 1:
                                System.out.println("메시지를 보낸 유저 이름: ");
                                String senderUser = sc.nextLine();
                                messageService.read(senderUser);
                                break;
                            case 2:

                                System.out.println("메시지 리스트: " + messageList.stream().collect(Collectors.toList()));
                                break;
                            case 3:
                                System.out.println("수정할 메시지 내용: ");
                                String modifyMessage = sc.nextLine();
                                messageService.modify(modifyMessage);
                                break;
                            case 4:
                                System.out.println("삭제할 메시지 내용: ");
                                String deleteMessage = sc.nextLine();
                                messageService.delete(deleteMessage);
                                break;
                            case 5:
                                runMsg = false;
                                System.out.println("종료");
                                break;
                            default:
                                System.out.println("잘못된 입력");
                                break;
                        }
                    }
                    break;
                case 5:
                    System.out.println("채널 이름 입력");
                    String channelName = sc.nextLine();
                    channelService.create(channelName);
                    break;
                case 6:
                    boolean runCh = true;
                    while(runCh){
                        System.out.println("1. 모든 채널");
                        System.out.println("2. 채널명 수정");
                        System.out.println("3. 채널 삭제");
                        System.out.println("4. 메뉴 복귀");
                        int ch_choice = sc.nextInt();
                        sc.nextLine();

                        switch (ch_choice) {
                            case 1:
                                System.out.println("채널 리스트: " + channelList.stream().collect(Collectors.toList()));
                                break;
                            case 2:
                                System.out.println("수정할 채널명: ");
                                String modifyChannelName = sc.nextLine();
                                channelService.modify(modifyChannelName);
                                break;
                            case 3:
                                System.out.println("삭제할 채널명: ");
                                String deleteChannelName = sc.nextLine();
                                channelService.delete(deleteChannelName);
                                break;
                            case 4:
                                runCh = false;
                                System.out.println("종료");
                                break;
                            default:
                                System.out.println("잘못된 입력");
                                break;
                        }
                    }
                    break;
                case 7:
                    run = false;
                    System.out.println("종료");
                    break;
                default:
                    System.out.println("잘못된 입력");
                    break;

            }
        }
    }
}
