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

                    boolean run2 = true;
                    while(run2){
                        System.out.println("1. 유저 리스트");
                        System.out.println("2. 유저 이름 수정");
                        System.out.println("3. 유저 삭제");
                        System.out.println("4. 메뉴 복귀");
                        int n2_choice = sc.nextInt();
                        sc.nextLine();

                        switch (n2_choice) {
                            case 1:

                                System.out.println("유저 리스트: " + userList.stream().collect(Collectors.toList()));
                                break;
                            case 2:
                                System.out.println("수정할 유저 이름: ");
                                String modifyUser = sc.nextLine();
                                //수정할 유저 이름 없는 경우 예외 추가해야함
                                userService.modify(modifyUser);
                                break;
                            case 3:
                                System.out.println("삭제할 유저 이름: ");
                                String deleteUser = sc.nextLine();
                                //삭제할 유저 이름 없는 경우 예외 추가해야함
                                userService.delete(deleteUser);
                                break;
                            case 4:
                                run2 = false;
                                System.out.println("종료");
                                break;
                            default:
                                System.out.println("잘못된 입력");
                                break;
                        }
                    }
                    break;

                case 3:                                         // 수정해야함

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

                    boolean run3 = true;
                    while(run3){
                        System.out.println("1. 모든 메시지");
                        System.out.println("2. 메시지 수정");
                        System.out.println("3. 메시지 삭제");
                        System.out.println("4. 메뉴 복귀");
                        int n2_choice = sc.nextInt();
                        sc.nextLine();

                        switch (n2_choice) {
                            case 1:

                                System.out.println("메시지 리스트: " + messageList.stream().collect(Collectors.toList()));
                                break;
                            case 2:
                                System.out.println("수정할 메시지 내용: ");
                                String modifyMessage = sc.nextLine();
                                //수정할 메시지 내용 없는 경우 예외 추가해야함
                                messageService.modify(modifyMessage);
                                break;
                            case 3:
                                System.out.println("삭제할 메시지 내용: ");
                                String deleteMessage = sc.nextLine();
                                //삭제할 메시지 내용 없는 경우 예외 추가해야함
                                messageService.delete(deleteMessage);
                                break;
                            case 4:
                                run3 = false;
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
                    boolean run4 = true;
                    while(run4){
                        System.out.println("1. 모든 채널");
                        System.out.println("2. 채널명 수정");
                        System.out.println("3. 채널 삭제");
                        System.out.println("4. 메뉴 복귀");
                        int n2_choice = sc.nextInt();
                        sc.nextLine();

                        switch (n2_choice) {
                            case 1:

                                System.out.println("채널 리스트: " + channelList.stream().collect(Collectors.toList()));
                                break;
                            case 2:
                                System.out.println("수정할 채널명: ");
                                String modifyChannelName = sc.nextLine();
                                //수정할 채널명 없는 경우 예외 추가해야함
                                channelService.modify(modifyChannelName);
                                break;
                            case 3:
                                System.out.println("삭제할 채널명: ");
                                String deleteChannelName = sc.nextLine();
                                //삭제할 채널명 없는 경우 예외 추가해야함
                                channelService.delete(deleteChannelName);
                                break;
                            case 4:
                                run4 = false;
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
