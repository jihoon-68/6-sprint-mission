package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.auth.request.LoginRequest;
import com.sprint.mission.discodeit.dto.auth.request.SignupRequest;
import com.sprint.mission.discodeit.dto.channel.model.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.response.ChannelFindAllResponse;
import com.sprint.mission.discodeit.dto.message.reqeust.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.user.model.UserDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

public class JavaApplication {
    private static final ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class);

    private static final UserService userService = context.getBean(BasicUserService.class);
    private static final ChannelService channelService = context.getBean(BasicChannelService.class);
    private static final MessageService messageService = context.getBean(BasicMessageService.class);
    private static final AuthService authService = context.getBean(AuthService.class);

    private static final Scanner in = new Scanner(System.in);

    private static com.sprint.mission.discodeit.dto.user.model.UserDto currentUser = null;
    private static ChannelDto currentChannel = null;

    public static void main(String[] args) {
        boolean run = true;

        while (run) {
            if (currentUser == null) {
//                System.out.println("=== [Test] 전체 사용자 ===");
//                System.out.println(userService.findAllUsers().toString());
//
//                System.out.println("=== [Test] 메시지 ===");
//                List<Message> messages =  messageService.findAllMessages();
//                System.out.println(messages);

                System.out.println("=== 로그인 메뉴 ===");
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("0. 종료");

                int menu = selectMenu(2);

                switch (menu) {
                    case 1: {
                        System.out.print("이메일: ");
                        String email = in.nextLine();
                        System.out.print("비밀번호: ");
                        String password = in.nextLine();

                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setEmail(email);
                        loginRequest.setPassword(password);

                        currentUser = authService.login(loginRequest);

                        if (currentUser != null) {
                            System.out.println("반갑습니다 " + currentUser.getUsername() + "님");
                        }

                        break;
                    }
                    case 2: {
                        System.out.print("이메일: ");
                        String email = in.nextLine();
                        System.out.print("비밀번호: ");
                        String password = in.nextLine();
                        System.out.print("이름: ");
                        String username = in.nextLine();

                        SignupRequest request = new SignupRequest();
                        request.setEmail(email);
                        request.setPassword(password);
                        request.setUsername(username);

                        authService.signup(request);
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
            if (currentUser == null) {
                return;
            }

            System.out.println("=== 메인 메뉴 ===");
            System.out.println("1. 채널");
            System.out.println("2. 사용자");
            System.out.println("0. 로그아웃");

            int menu = selectMenu(2);

            switch (menu) {
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
            System.out.println("1. 메시지");
            System.out.println("2. 내 정보");
            System.out.println("3. 친구 관리");
            System.out.println("0. 뒤로가기");

            int menu = selectMenu(3);

            switch (menu) {
                case 1:
                    showUserMessageMenu();
                    break;
                case 2:
                    showUserInfoMenu();
                    if (currentUser == null) {
                        return;
                    }
                    break;
                case 3:
                    showFriendMenu();
                    break;
                default:
                    return;
            }
        }
    }

    private static void showFriendMenu() {
        while (true) {
            System.out.println("1. 친구 추가");
            System.out.println("2. 받은 요청");
            System.out.println("3. 보낸 요청");
            System.out.println("4. 친구 삭제");
            System.out.println("5. 친구 목록");
            System.out.println("0. 뒤로 가기");

            int menu = selectMenu(5);

            switch (menu) {
                case 1: {
                    System.out.print("이메일: ");
                    String email = in.nextLine();

                    userService.sendFriendRequest(currentUser.getId(), email);
                    break;
                }
                case 2: {
                    List<UUID> receivedFriendRequests = currentUser.getReceivedFriendRequests();
                    List<UUID> copyReceivedFriendRequests = new ArrayList<>(receivedFriendRequests);

                    if (receivedFriendRequests.isEmpty()) {
                        System.out.println("받은 친구 요청이 없습니다.");
                        break;
                    }

                    for (UUID receivedFriendRequest : copyReceivedFriendRequests) {
                        UserDto friendUser = userService.findUserById(receivedFriendRequest);
                        String email;
                        String username;

                        if (friendUser == null) {
                            email = "";
                            username = "(알 수 없음)";
                        } else {
                            email = friendUser.getEmail();
                            username = friendUser.getUsername();
                        }

                        System.out.println(username + " / " + email);
                        System.out.print("수락하시겠습니까?(y/n): ");
                        char c = in.next().charAt(0);

                        if (c == 'y' || c == 'Y') {
                            userService.acceptFriendRequest(currentUser.getId(), receivedFriendRequest);
                        } else {
                            userService.rejectFriendRequest(currentUser.getId(), receivedFriendRequest);
                        }
                    }
                    break;
                }
                case 3:
                    List<UUID>  sentFriendRequests = currentUser.getSentFriendRequests();
                    List<UUID> copySentFriendRequests = new ArrayList<>(sentFriendRequests);
                    if (sentFriendRequests.isEmpty()) {
                        System.out.println("보낸 요청이 없습니다.");
                        break;
                    }
                    for (UUID friendId : copySentFriendRequests) {
                        UserDto friendUser = userService.findUserById(friendId);
                        String email;
                        String username;

                        if (friendUser == null) {
                            email = "";
                            username = "(알 수 없음)";
                        } else {
                            email = friendUser.getEmail();
                            username = friendUser.getUsername();
                        }

                        System.out.println(username + " / " + email);
                        System.out.print("취소하시겠습니까?(y/n): ");
                        char c = in.next().charAt(0);

                        if (c == 'y' || c == 'Y') {
                            userService.cancelSendFriendRequest(currentUser.getId(), friendId);
                        }
                    }
                    break;
                case 4: {
                    List<UUID> friendIds = currentUser.getFriendIds();

                    if (friendIds.isEmpty()) {
                        System.out.println("친구가 없어요..");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < friendIds.size(); i++) {
                        UserDto user = userService.findUserById(friendIds.get(i));
                        String email;
                        String username;
                        if (user == null) {
                            email = "";
                            username = "(알 수 없음)";
                        } else {
                            email = user.getEmail();
                            username = user.getUsername();
                        }

                        System.out.println((i + 1) + ". " + username + " / " + email);
                    }
                    System.out.println("----------------");

                    int index = selectIndex(friendIds.size());

                    userService.deleteFriend(currentUser.getId(), friendIds.get(index));

                    break;
                }
                case 5: {
                    List<UUID> friendIds = currentUser.getFriendIds();

                    if (friendIds.isEmpty()) {
                        System.out.println("친구가 없어요..");
                        break;
                    }

                    System.out.println("----------------");
                    for (UUID friendId : friendIds) {
                        UserDto user = userService.findUserById(friendId);
                        String email;
                        String username;
                        String status;

                        if (user == null) {
                            email = "";
                            username = "(알 수 없음)";
                            status = "Offline";
                        } else {
                            email = user.getEmail();
                            username = user.getUsername();
                            status = user.getUserStatus();
                        }

                        System.out.println(username + " / " + email + " / " + status);
                    }
                    System.out.println("----------------");
                    break;
                }
                default:
                    return;
            }
        }
    }

    private static void showUserMessageMenu() {
        UUID friendId = selectFriend();

        if (friendId == null) {
            return;
        }

        while (true) {
            System.out.println("1. 메시지 작성");
            System.out.println("2. 메시지 변경");
            System.out.println("3. 메시지 삭제");
            System.out.println("4. 메시지 목록");
            System.out.println("0. 뒤로 가기");

            int menu = selectMenu(4);

            switch (menu) {
                case 1:
                    System.out.print("내용: ");
                    String content = in.nextLine();
                    String file = null;
                    List<String> filePath = new ArrayList<>();

                    while(!Objects.equals(file, "y") && !Objects.equals(file, "n")) {
                        System.out.print("파일 첨부 하시겠습니까?(y/n): ");
                        file = in.nextLine();
                    }

                    if (Objects.equals(file, "y")) {
                        while (true) {
                            System.out.println("경로를 입력해 주세요(종료 0): ");
                            String path =  in.nextLine();
                            if (path.equals("0")) {
                                break;
                            }
                        }
                    }

                    messageService.createMessage(currentUser.getId(), null, friendId, content, filePath);
                    break;
                case 2: {
                    List<Message> myMessages = messageService.findMyMessagesToFriend(currentUser.getId(), friendId);

                    System.out.println("----------------");
                    for (int i = 0; i < myMessages.size(); i++) {
                        System.out.println((i + 1) + ". " + myMessages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(myMessages.size());

                    System.out.print("내용: ");
                    String newContent = in.nextLine();

                    MessageUpdateRequest request = new MessageUpdateRequest();
                    request.setId(myMessages.get(index).getId());
                    request.setContent(newContent);

                    messageService.updateContent(request);
                    break;
                }
                case 3: {
                    List<Message> myMessages = messageService.findMyMessagesToFriend(currentUser.getId(), friendId);

                    System.out.println("----------------");
                    for (int i = 0; i < myMessages.size(); i++) {
                        System.out.println((i + 1) + ". " + myMessages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(myMessages.size());

                    messageService.deleteMessageById(myMessages.get(index).getId());
                    break;
                }
                case 4:
                    List<Message> conversation = messageService.findFriendConversation(currentUser.getId(), friendId);

                    System.out.println("----------------");
                    for (Message message : conversation) {
                        UUID id = message.getAuthorId();
                        String authorName;
                        if (message.isDrawnAuthor()) {
                            authorName = "(알 수 없음)";
                        } else {
                            UserDto user =  userService.findUserById(id);
                            authorName = user.getUsername();
                        }

                        System.out.println(authorName + ": " + message.getContent());
                    }
                    System.out.println("----------------");

                    break;
                default:
                    return;
            }
        }
    }

    private static UUID selectFriend() {
        if (currentUser.getFriendIds().isEmpty()) {
            System.out.println("친구가 없어요..");
            return null;
        }

        System.out.println("----------------");
        for (int i = 0; i < currentUser.getFriendIds().size(); i++) {
            UserDto user = userService.findUserById(currentUser.getFriendIds().get(i));
            String friendName;
            String status;

            if (user == null) {
                friendName = "(알 수 없음)";
                status = "Offline";
            } else {
                friendName = user.getUsername();
                status = user.getUserStatus();
            }

            System.out.println((i + 1) + ". " + friendName + " / " + status);
        }
        System.out.println("----------------");

        int index = selectIndex(currentUser.getFriendIds().size());

        return currentUser.getFriendIds().get(index);
    }

    private static void showUserInfoMenu() {
        while (true) {
            System.out.println("----------------");
            System.out.println(currentUser.toString());
            System.out.println("----------------");

            System.out.println("1. 정보 변경");
            System.out.println("2. 회원 탈퇴");
            System.out.println("0. 뒤로가기");

            int menu = selectMenu(2);

            switch (menu) {
                case 1:
                    userInfoManager();
                    break;
                case 2:
                    List<UUID> channelIds = channelService.findAllByOwnerId(currentUser.getId()).getChannels()
                            .stream().map(ChannelDto::getChannelId).toList();

                    messageService.deleteAllByChannelIds(channelIds);
                    channelService.deleteChannelByOwnerId(currentUser.getId());
                    messageService.anonymizeUserMessage(currentUser.getId());
                    messageService.cleanupDM();
                    userService.deleteUserById(currentUser.getId());
                    currentUser = null;
                    return;
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

            int menu = selectMenu(3);

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

            int menu = selectMenu(3);

            switch (menu) {
                case 1:
                    showMyChannelMenu();
                    break;
                case 2: {
                    ChannelFindAllResponse response = channelService.findAllByUserId(currentUser.getId());
                    List<ChannelDto> channels = response.getChannels();

                    if (channels.isEmpty()) {
                        System.out.println("참여할 수 있는 채널이 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < channels.size(); i++) {
                        UserDto user = userService.findUserById(channels.get(i).getOwnerId());
                        System.out.println(channels.get(i).getOwnerId());
                        System.out.println((i + 1) + ". 채널명: " + channels.get(i).getChannelName() + " / 소유자:  " + user.getUsername());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(channels.size());

                    currentChannel = channels.get(index);

                    enterChannel();
                    break;
                }
                case 3: {
                    ChannelFindAllResponse response = channelService.findAllByUserId(currentUser.getId());
                    List<ChannelDto> channels = response.getChannels();

                    System.out.println("----------------");
                    for (int i = 0; i < channels.size(); i++) {
                        UserDto user = userService.findUserById(channels.get(i).getOwnerId());
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

            int menu = selectMenu(3);

            switch (menu) {
                case 1:
                    System.out.print("채널 이름: ");
                    String channelName = in.nextLine();
                    String channelType = "";
                    boolean isPrivate = false;

                    while(!Objects.equals(channelType, "y") && !Objects.equals(channelType, "n")) {
                        System.out.println("Private/Public(y/n): ");
                        channelType = in.nextLine();
                    }

                    if (channelType.equals("y")) {
                        isPrivate = true;
                    }

                    ChannelCreateRequest request = new ChannelCreateRequest();
                    request.setChannelName(channelName);
                    request.setPrivate(isPrivate);
                    request.setOwnerId(currentUser.getId());

                    channelService.createChannel(request);
                    break;
                case 2: {
                    ChannelFindAllResponse response = channelService.findAllByOwnerId(currentUser.getId());
                    List<ChannelDto> myChannels = response.getChannels();

                    if (myChannels.isEmpty()) {
                        System.out.println("채널을 만들고 시작해주세요.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < myChannels.size(); i++) {
                        System.out.println((i + 1) + ". 채널명: " + myChannels.get(i).getChannelName());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(myChannels.size());

                    currentChannel = myChannels.get(index);

                    showMyChannelManager();
                    break;
                }
                case 3: {
                    ChannelFindAllResponse response = channelService.findAllByOwnerId(currentUser.getId());
                    List<ChannelDto> myChannels = response.getChannels();

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

            int menu = selectMenu(4);

            switch (menu) {
                case 1:
                    System.out.print("내용: ");
                    String content = in.nextLine();
                    String file = null;
                    List<String> filePath = new ArrayList<>();

                    while(!Objects.equals(file, "y") && !Objects.equals(file, "n")) {
                        System.out.print("파일 첨부 하시겠습니까?(y/n): ");
                        file = in.nextLine();
                    }

                    if (Objects.equals(file, "y")) {
                        while (true) {
                            System.out.println("경로를 입력해 주세요(종료 0): ");
                            String path =  in.nextLine();
                            if (path.equals("0")) {
                                break;
                            }
                        }
                    }

                    messageService.createMessage(currentUser.getId(), currentChannel.getChannelId(), null, content, filePath);
                    break;
                case 2: {
                    List<Message> messages = messageService.findAllByAuthorIdAndChannelId(currentUser.getId(), currentChannel.getChannelId());

                    if (messages.isEmpty()) {
                        System.out.println("수정할 수 있는 메시지가 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < messages.size(); i++) {
                        System.out.println((i + 1) + ". " + messages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(messages.size());

                    System.out.print("새로운 내용: ");
                    String newContent = in.nextLine();

                    MessageUpdateRequest request = new MessageUpdateRequest();
                    request.setId(messages.get(index).getId());
                    request.setContent(newContent);

                    messageService.updateContent(request);

                    break;
                }
                case 3: {
                    List<Message> messages;

                    if (!currentChannel.getOwnerId().equals(currentUser.getId())) {
                        messages = messageService.findAllByAuthorIdAndChannelId(currentUser.getId(), currentChannel.getChannelId());
                    } else {
                        messages = messageService.findAllByChannelId(currentChannel.getChannelId());
                    }

                    if (messages.isEmpty()) {
                        System.out.println("삭제할 수 있는 메시지가 없습니다.");
                        break;
                    }

                    System.out.println("----------------");
                    for (int i = 0; i < messages.size(); i++) {
                        UserDto user = userService.findUserById(messages.get(i).getAuthorId());
                        String username;
                        if (user == null) {
                            username = "(알 수 없음)";
                        } else {
                            username = user.getUsername();
                        }
                        System.out.println((i + 1) + ". " + username + ": " + messages.get(i).getContent());
                    }
                    System.out.println("----------------");

                    int index = selectIndex(messages.size());

                    messageService.deleteMessageById(messages.get(index).getId());
                    break;
                }
                case 4: {
                    List<Message> messages = messageService.findAllByChannelId(currentChannel.getChannelId());

                    if (messages.isEmpty()) {
                        System.out.println("채널에 메시지를 작성해 보세요!");
                        break;
                    }

                    System.out.println("----------------");
                    for (Message m : messages) {
                        UserDto user = userService.findUserById(m.getAuthorId());
                        String username;
                        if (user == null) {
                            username = "(알 수 없음)";
                        } else {
                            username = user.getUsername();
                        }
                        System.out.println(username + ": " + m.getContent());
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

            int menu = selectMenu(2);

            switch (menu) {
                case 1:
                    System.out.print("새로운 채널 이름: ");
                    String channelName = in.nextLine();

                    ChannelUpdateRequest request = new ChannelUpdateRequest();
                    request.setChannelId(currentChannel.getChannelId());
                    request.setChannelName(channelName);
                    request.setOwnerId(currentUser.getId());

                    channelService.updateChannelName(request);
                    break;
                case 2:
                    messageService.deleteAllByChannelIds(List.of(currentChannel.getChannelId()));
                    channelService.deleteChannelById(currentChannel.getChannelId());

                    currentChannel = null;
                    return;
                default:
                    currentChannel = null;
                    return;
            }
        }
    }

    private static int selectMenu(int high) {
        while(true) {
            try {
                System.out.print("선택해주세요: ");
                int menu = in.nextInt();

                if (menu > high || menu < 0) {
                    throw new InputMismatchException();
                }

                in.nextLine();

                return menu;
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static int selectIndex(int high) {
        while(true) {
            try {
                System.out.print("번호 선택: ");
                int index =  in.nextInt();
                if (index > high || index < 1) {
                    throw new InputMismatchException();
                }

                in.nextLine();
                return index - 1;
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
