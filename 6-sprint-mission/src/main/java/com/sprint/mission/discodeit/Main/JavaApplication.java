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
import java.util.UUID;
import java.util.stream.Collectors;

public class JavaApplication {
    public static void main(String[] args) {
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();
        UserService userService = new JCFUserService();

        List<User> userData = userService.allRead();
        List<Message> messageData = messageService.allRead();
        List<Channel> channelData = channelService.allRead();

        UUID id = null;
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
                    if(userName.isEmpty()){
                        System.out.println("입력이 비어있습니다 ");
                        break;
                    }
                    System.out.println(userService.create(userName));
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
                                if(readUser.isEmpty() || userService.read(readUser)==null){
                                    System.out.println("입력이 비어있거나 해당 유저가 없습니다 ");
                                    break;
                                }
                                System.out.println(userService.read(readUser));
                                break;
                            case 2:
                                System.out.println("유저 리스트: " + userData.stream().collect(Collectors.toList()));
                                break;
                            case 3:
                                System.out.println("이름을 수정할 유저 id: ");
                                String inputUserId = sc.nextLine();
                                System.out.println("어떤 이름으로 수정하겠습니까? ");
                                String modifyUserName = sc.nextLine();
                                if(inputUserId.isEmpty() || modifyUserName.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID modifyUserId = UUID.fromString(inputUserId);
                                User modifyUser = userService.modify(modifyUserId);
                                if(modifyUser == null){
                                    System.out.println("수정할 유저가 존재하지 않습니다 ");
                                    break;
                                }
                                modifyUser.setName(modifyUserName);
                                System.out.println(modifyUser);
                                break;
                            case 4:
                                System.out.println("삭제할 유저의 id: ");
                                inputUserId = sc.nextLine();
                                if(inputUserId.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID deleteUserId = UUID.fromString(inputUserId);
                                User deletedUser = userService.delete(deleteUserId);
                                if(deletedUser == null){
                                    System.out.println("id에 해당하는 유저가 없습니다 ");
                                    break;
                                }
                                userData.remove(deletedUser);
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
                    User sender = userService.read(send);
                    if(sender==null){
                        System.out.println("해당 유저가 없습니다");
                        break;
                    }

                    System.out.println("받는 사람: ");
                    String recieve = sc.nextLine();
                    User reciever = userService.read(recieve);
                    if(reciever ==null){
                        System.out.println("해당 유저가 없습니다");
                        break;
                    }

                    System.out.println("메시지 내용: ");
                    String content = sc.nextLine();
                    System.out.println("메시지 보내는 채널명: ");
                    String messageInCh = sc.nextLine();
                    if(content.isEmpty() || messageInCh.isEmpty()){
                        System.out.println("입력이 비어있습니다 ");
                        break;
                    }
                    Channel messageInChannel = channelService.read(messageInCh);
                    if(messageInChannel ==null){
                        System.out.println("해당 채널이 없습니다");
                        break;
                    }
                    System.out.println(messageService.create(sender, reciever, content, messageInChannel));
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
                                Message userMsg = messageService.read(senderUser);
                                if(senderUser.isEmpty() || userMsg==null){
                                    System.out.println("입력이 비어있거나 유저가 존재하지 않습니다 ");
                                    break;
                                }
                                System.out.println(messageService.read(senderUser));
                                break;
                            case 2:

                                System.out.println("메시지 리스트: " + messageData.stream().collect(Collectors.toList()));
                                break;
                            case 3:
                                System.out.println("내용을 수정할 메시지 id: ");
                                String inputMessageId = sc.nextLine();
                                System.out.println("메시지 수정 입력: ");
                                String modifyMsg = sc.nextLine();
                                if(inputMessageId.isEmpty() || modifyMsg.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID modifyMessageId = UUID.fromString(inputMessageId);
                                Message modifiedMessage = messageService.modify(modifyMessageId);
                                if (modifiedMessage == null){
                                    System.out.println("수정할 메시지가 존재하지 않습니다");
                                    break;
                                }
                                modifiedMessage.setContent(modifyMsg);
                                System.out.println(modifiedMessage);
                                break;
                            case 4:
                                System.out.println("삭제할 메시지의 id: ");
                                inputMessageId = sc.nextLine();
                                if(inputMessageId.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID deleteMessageId = UUID.fromString(inputMessageId);
                                Message deletedMessage = messageService.delete(deleteMessageId);
                                if(deletedMessage == null){
                                    System.out.println("id에 해당하는 메시지가 없습니다 ");
                                    break;
                                }
                                messageData.remove(deletedMessage);
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
                    System.out.println(channelService.create(channelName));
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
                                System.out.println("채널 리스트: " + channelData.stream().collect(Collectors.toList()));
                                break;
                            case 2:
                                System.out.println("이름을 수정할 채널 id: ");
                                String inputChannelId = sc.nextLine();
                                System.out.println("채널명 수정 입력: ");
                                String msg = sc.nextLine();
                                if (msg.isEmpty() || inputChannelId.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID modifyChannelId = UUID.fromString(inputChannelId);
                                Channel modifyChannel = channelService.modify(modifyChannelId);
                                if (modifyChannel == null){
                                    System.out.println("수정할 채널이 존재하지 않습니다");
                                    break;
                                }
                                modifyChannel.setName(msg);
                                break;
                            case 3:
                                System.out.println("삭제할 채널 id: ");
                                inputChannelId = sc.nextLine();
                                if(inputChannelId.isEmpty()){
                                    System.out.println("입력이 비어있습니다 ");
                                    break;
                                }
                                UUID deleteChannelId = UUID.fromString(inputChannelId);
                                Channel deletedChannel = channelService.delete(deleteChannelId);
                                if(deletedChannel == null){
                                    System.out.println("id에 해당하는 채널이 없습니다 ");
                                    break;
                                }
                                channelData.remove(deletedChannel);
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
