package com.sprint.mission.discodeit;

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

public class JavaApplication {
    private static final UserService userService = new JCFUserService();
    private static final ChannelService channelService = new JCFChannelService();
    private static final MessageService messageService = new JCFMessageService();
    private static final Scanner in = new Scanner(System.in);

    private static User currentUser = null;
    private static Channel currentChannel = null;

    public static void main(String[] args) {
        int mainMenu;
        boolean run = true;

        while (run) {
            if (currentUser == null) {
                int menu;
                System.out.println("=== 로그인 메뉴 ===");
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("0. 종료");
                System.out.print("선택해주세요: ");
                menu = in.nextInt();
                in.nextLine();

                switch (menu) {
                    case 1: {
                        System.out.print("이메일: ");
                        String email = in.nextLine();
                        System.out.print("비밀번호: ");
                        String password = in.nextLine();

                        currentUser = userService.login(email, password);
                        break;
                    }
                    case 2: {
                        System.out.print("이메일: ");
                        String email = in.nextLine();
                        System.out.print("비밀번호: ");
                        String password = in.nextLine();
                        System.out.print("이름: ");
                        String username = in.nextLine();

                        userService.signup(email, password, username);
                        break;
                    }
                    default:
                        run = false;
                        break;
                }
            } else {
                showMainMenu();
            }
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("=== 메인 메뉴 ===");
            System.out.println("1. 채널");
            System.out.println("2. 사용자");
            System.out.println("0. 로그아웃");
            System.out.print("선택해주세요: ");

            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    showChannelMenu();
                    break;
                case 2:
                    showUserMenu();
                    break;
                case 0:
                    currentUser = null;
                    return;
            }
        }
    }

    private static void showUserMenu() {
        while (true) {
            System.out.println("=== 사용자 메뉴 ===");
            System.out.println("1. 정보 변경");
            System.out.println("2. 회원 탈퇴");
            System.out.println("3. 내 정보");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    userInfoManager();
                    break;
                case 2:
                    userService.deleteUserById(currentUser.getId());
                    currentUser = null;
                    break;
                case 3:
                    System.out.println("----------------");
                    System.out.println(userService.findUserById(currentUser.getId()));
                    System.out.println("----------------");
                    break;
                default:
                    return;
            }
        }
    }

    private static void userInfoManager() {
        while (true) {
            System.out.println("1. 이메일 변경");
            System.out.println("2. 이름 변경");
            System.out.println("3. 비밀번호 변경");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("새로운 이메일: ");
                    String email = in.nextLine();
                    userService.updateEmail(email, currentUser.getId());
                    break;
                case 2:
                    System.out.print("새로운 이름: ");
                    String username = in.nextLine();
                    userService.updateUsername(username, currentUser.getId());
                    break;
                case 3:
                    System.out.print("새로운 비밀번호: ");
                    String password = in.nextLine();
                    userService.updatePassword(password, currentUser.getId());
                    break;
                default:
                    return;
            }
        }
    }


    private static void showChannelMenu() {
        while (true) {
            System.out.println("=== 채널 메뉴 ===");
            System.out.println("1. 내 채널");
            System.out.println("2. 채널 참여");
            System.out.println("3. 채널 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    showMyChannelMenu();
                    break;
                case 2: {
                    List<Channel> channels = channelService.findAllChannels();

                    if (channels.isEmpty()) {
                        System.out.println("참여할 수 있는 채널이 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < channels.size(); i++) {
                        User user = userService.findUserById(channels.get(i).getOwnerId());
                        System.out.println((i + 1) + ". 채널명: " + channels.get(i).getChannelName() + " / 소유자:  " + user.getUsername());
                    }
                    System.out.println("----------------");

                        System.out.print("채널 번호 선택: ");
                        int index = in.nextInt() - 1;
                        in.nextLine();

                        if (index < 0 || index >= channels.size()) {
                            System.out.println("잘못된 번호입니다.");
                            break;
                        }


                    currentChannel = channels.get(index);

                    enterChannel();
                    break;
                }
                case 3: {
                    List<Channel> channels = channelService.findAllChannels();
                    System.out.println("----------------");
                    for (int i = 0; i < channels.size(); i++) {
                        User user = userService.findUserById(channels.get(i).getOwnerId());
                        System.out.println((i + 1) + ". 채널명: " + channels.get(i).getChannelName() + " / 소유자:  " + user.getUsername());
                    }
                    System.out.println("----------------");
                    break;
                }
                default:
                    return;
            }
        }
    }

    private static void showMyChannelMenu() {
        while (true) {
            System.out.println("=== 내 채널 ===");
            System.out.println("1. 내 채널 생성");
            System.out.println("2. 내 채널 관리");
            System.out.println("3. 내 채널 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("채널 이름: ");
                    String channelName = in.nextLine();
                    channelService.createChannel(channelName, currentUser.getId());
                    break;
                case 2: {
                    List<Channel> myChannels = channelService.findChannelByOwnerId(currentUser.getId());

                    if(myChannels.isEmpty()) {
                        System.out.println("채널을 만들고 시작해주세요.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < myChannels.size(); i++) {
                        System.out.println((i + 1) + ". 채널명: " + myChannels.get(i).getChannelName());
                    }
                    System.out.println("----------------");

                    System.out.print("채널 번호 선택: ");
                    int index = in.nextInt() - 1;
                    in.nextLine();

                    if (index < 0 || index >= myChannels.size()) {
                        System.out.println("잘못된 번호입니다.");
                        break;
                    }

                    currentChannel = myChannels.get(index);

                    showMyChannelManager();
                    break;
                }
                case 3: {
                    List<Channel> myChannels = channelService.findChannelByOwnerId(currentUser.getId());

                    if (myChannels.isEmpty()) {
                        System.out.println("채널을 만들어 보세요!");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < myChannels.size(); i++) {
                        System.out.println((i + 1) + ". 채널명: " + myChannels.get(i).getChannelName());
                    }
                    System.out.println("----------------");
                    break;
                }
                default:
                    return;
            }
        }
    }

    private static void enterChannel() {
        while (true) {
            System.out.println("=== " + currentChannel.getChannelName() + " ===");
            System.out.println("1. 메시지 작성");
            System.out.println("2. 메시지 수정");
            System.out.println("3. 메시지 삭제");
            System.out.println("4. 메시지 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("내용: ");
                    String content = in.nextLine();

                    messageService.createMessage(currentUser.getId(), currentChannel.getId(), content);
                    break;
                case 2: {
                    List<Message> messages = messageService.findMessagesByAuthorIdAndChannelId(currentUser.getId(), currentChannel.getId());

                    if (messages.isEmpty()) {
                        System.out.println("수정할 수 있는 메시지가 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < messages.size(); i++) {
                        System.out.println((i + 1) + ". " + messages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    System.out.print("번호 선택: ");
                    int index = in.nextInt() - 1;
                    in.nextLine();

                    if (index < 0 || index >= messages.size()) {
                        System.out.println("잘못된 번호입니다.");
                        break;
                    }

                    System.out.print("새로운 내용: ");
                    String newContent = in.nextLine();

                    messageService.updateContent(newContent, messages.get(index).getId());

                    break;
                }
                case 3: {
                    List<Message> messages;

                    if (!currentChannel.getOwnerId().equals(currentUser.getId())) {
                        messages = messageService.findMessagesByAuthorIdAndChannelId(currentUser.getId(), currentChannel.getId());
                    } else {
                        messages = messageService.findMessagesByChannelId(currentChannel.getId());
                    }

                    if (messages.isEmpty()) {
                        System.out.println("삭제할 수 있는 메시지가 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < messages.size(); i++) {
                        System.out.println((i + 1) + ". " + messages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    System.out.print("번호 선택: ");
                    int index = in.nextInt() - 1;
                    in.nextLine();

                    if (index < 0 || index >= messages.size()) {
                        System.out.println("잘못된 번호입니다.");
                        break;
                    }

                    messageService.deleteMessage(messages.get(index).getId());
                    break;
                }
                case 4: {
                    List<Message> messages = messageService.findMessagesByChannelId(currentChannel.getId());

                    if (messages.isEmpty()) {
                        System.out.println("채널에 메시지를 작성해 보세요!");
                        break;
                    }

                    System.out.println("----------------");
                    for (Message m :  messages) {
                        User user = userService.findUserById(m.getAutherId());
                        System.out.println(user.getUsername() + " : " + m.getContent());
                    }
                    System.out.println("----------------");
                    break;
                }
                default:
                    currentChannel = null;
                    return;
            }
        }
    }

    private static void showMyChannelManager() {
        while (true) {
            System.out.println("=== 내 채널 관리 ===");
            System.out.println("1. 채널 이름 변경");
            System.out.println("2. 채널 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택해주세요: ");

            int menu = in.nextInt();
            in.nextLine();

            switch (menu) {
                case 1:
                    System.out.print("새로운 채널 이름: ");
                    String channelName = in.nextLine();

                    channelService.updateChannelName(currentChannel.getId(), channelName);
                    break;
                case 2:
                    channelService.deleteChannel(currentChannel.getId());

                    currentChannel = null;
                    return;
                default:
                    currentChannel = null;
                    return;
            }
        }
    }
}
