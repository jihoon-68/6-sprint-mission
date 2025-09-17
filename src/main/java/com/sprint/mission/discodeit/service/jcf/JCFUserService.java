package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.user.model.UserDto;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateProfileImageRequest;
import com.sprint.mission.discodeit.dto.user.response.UserFindAllResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JCFUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository  userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void updateUsername(String username, UUID id) {
        if (userRepository.existsById(id)) {
            User updateUser = userRepository.findById(id);
            updateUser.updateUsername(username);

            userRepository.save(updateUser);
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void updatePassword(String password, UUID id) {
        if (password.length() < 6) {
            System.out.println("[Error] 비밀번호는 6글자 이상이여야 합니다.");
            return;
        }

        if (userRepository.existsById(id)) {
            User updateUser = userRepository.findById(id);
            updateUser.updatePassword(password);
            System.out.println("[Info] 비밀번호가 변경되었습니다.");
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void updateEmail(String email, UUID id) {
        if (email.contains(" ")) {
            System.out.println("[Error] 이메일에 공백을 포함할 수 없습니다.");
            return;
        }

        if (!userRepository.existsById(id)) {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
            return;
        }

        if (userRepository.existsByEmail(email)) {
            System.out.println("[Error] 이미 사용중인 이메일 입니다.");
            return;
        }

        User updateUser = userRepository.findById(id);
        updateUser.updateEmail(email);
        userRepository.save(updateUser);
        System.out.println("[Info] 이메일이 변경되었습니다.");
    }

    @Override
    public void updateProfileImage(UserUpdateProfileImageRequest request) {
        BinaryContent content = binaryContentRepository.findByUserId(request.getUserId());

        if (content == null) {
            System.out.println("[Error] 프로필을 찾지 못했습니다.");
            return;
        }

        binaryContentRepository.save(content);
        System.out.println("[Info] 프로필이 변경되었습니다.");
    }

    @Override
    public void deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println("[Info] 유저가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 유저 삭제에 실패했습니다.");
        }
    }

    @Override
    public UserDto findUserById(UUID id) {
        User user = userRepository.findById(id);
        UserStatus status = userStatusRepository.findByUserId(id);

        UserDto response = new UserDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFriendIds(user.getFriendIds());
        response.setReceivedFriendRequests(user.getReceivedFriendRequests());
        response.setSentFriendRequests(user.getSentFriendRequests());

        Instant minusFiveMinutes = Instant.now().minusSeconds(300);

        if (status.isLogin() || (status.getLastLogin() != null && status.getLastLogin().isAfter(minusFiveMinutes))) {
            response.setUserStatus("Online");
        } else {
            response.setUserStatus("Offline");
        }

        return response;
    }

    @Override
    public UserFindAllResponse findAllUsers() {
        List<com.sprint.mission.discodeit.dto.user.model.UserDto> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserStatus status = userStatusRepository.findByUserId(user.getId());

            com.sprint.mission.discodeit.dto.user.model.UserDto userDto = new com.sprint.mission.discodeit.dto.user.model.UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setFriendIds(user.getFriendIds());
            userDto.setReceivedFriendRequests(user.getReceivedFriendRequests());
            userDto.setSentFriendRequests(user.getSentFriendRequests());
            users.add(userDto);

            Instant minusFiveMinutes = Instant.now().minusSeconds(300); //현재에서 5분 빼기

            if (status.isLogin() || (status.getLastLogin() != null && status.getLastLogin().isAfter(minusFiveMinutes))) {
                userDto.setUserStatus("Online");
            } else {
                userDto.setUserStatus("Offline");
            }

            users.add(userDto);
        }

        UserFindAllResponse response = new UserFindAllResponse();
        response.setUsers(users);

        return response;
    }

    @Override
    public void sendFriendRequest(UUID userId, String email) {
        User friendUser = userRepository.findByEmail(email);
        User currentUser = userRepository.findById(userId);

        if (friendUser == null) {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
            return;
        }

        if (email.equals(currentUser.getEmail())) {
            System.out.println("[Error] 자기 자신은 추가 할 수 없습니다.");
            return;
        }

        if (currentUser.getSentFriendRequests().contains(friendUser.getId())) {
            System.out.println("[Error] 이미 처리중인 요청입니다.");
            return;
        }

        if (currentUser.getFriendIds().contains(friendUser.getId())) {
            System.out.println("[Error] 이미 추가된 친구입니다.");
            return;
        }

        friendUser.getReceivedFriendRequests().add(userId);
        currentUser.getSentFriendRequests().add(friendUser.getId());

        userRepository.save(friendUser);
        userRepository.save(currentUser);

        System.out.println("[Info] 친구 요청을 보냈습니다.");
    }

    @Override
    public void acceptFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser = userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getReceivedFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getFriendIds().add(friendId);
        currentUser.getReceivedFriendRequests().remove(friendId);


        friendUser.getFriendIds().add(userId);
        friendUser.getSentFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);

        System.out.println("[Info] 친구 추가가 완료되었습니다.");
    }

    @Override
    public void rejectFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getReceivedFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getReceivedFriendRequests().remove(friendId);
        friendUser.getSentFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }

    @Override
    public void cancelSendFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getSentFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getSentFriendRequests().remove(friendId);
        friendUser.getReceivedFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }

    @Override
    public void deleteFriend(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getFriendIds().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getFriendIds().remove(friendId);
        friendUser.getFriendIds().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);

        System.out.println("[Info] 친구 삭제가 완료되었습니다.");
    }
}
