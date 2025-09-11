package com.sprint.mission.discodeit.service.basic;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void updateUsername(String username, UUID id) {
        User user = userRepository.findById(id);

        if (user == null) {
            log.warn("User를 찾을 수 없습니다. - userId: {}", id);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        user.updateUsername(username);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String password, UUID id) {
        if (password.length() < 6) {
            log.info("잘못된 password 형식 - userId: {}", id);
            throw new IllegalArgumentException("password는 6자 이상이여야 합니다.");
        }

        User user = userRepository.findById(id);

        if (user == null) {
            log.warn("User를 찾을 수 없습니다. - userId: {}", id);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        user.updatePassword(password);
    }

    @Override
    public void updateEmail(String email, UUID id) {
        User user = userRepository.findById(id);

        if (user == null) {
            log.warn("User를 찾을 수 없습니다. - userId: {}", id);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        if (userRepository.existsByEmail(email)) {
            log.info("중복된 email - userId: {}", id);
            throw new IllegalStateException("이미 사용중인 email입니다.");
        }

        if (email.contains(" ")) {
            log.info("잘못된 email 형식 - userId: {}", id);
            throw new IllegalArgumentException("email에 공백을 포함할 수 없습니다.");
        }

        user.updateEmail(email);
        userRepository.save(user);
    }

    @Override
    public void updateProfileImage(UserUpdateProfileImageRequest request) {
        BinaryContent content = binaryContentRepository.findByUserId(request.getUserId());

        if (content == null) {
            content = new BinaryContent(request.getUserId(), null, request.getPath());
            binaryContentRepository.save(content);
        } else {
            content.setPath(request.getPath());
            binaryContentRepository.save(content);
        }
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id);

        if (user == null) {
            log.warn("User를 찾을 수 없습니다. - userId: {}", id);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        binaryContentRepository.deleteByUserId(id);
        userStatusRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findUserById(UUID id) {
        User user = userRepository.findById(id);

        if (user == null) {
            log.warn("User를 찾을 수 없습니다. -  userId: {}", id);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(id);

        UserDto response = new UserDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFriendIds(user.getFriendIds());
        response.setReceivedFriendRequests(user.getReceivedFriendRequests());
        response.setSentFriendRequests(user.getSentFriendRequests());

        Instant minusFiveMinutes = Instant.now().minusSeconds(300);

        if (userStatus.isLogin() || (userStatus.getLastLogin() != null && userStatus.getLastLogin().isAfter(minusFiveMinutes))) {
            response.setUserStatus("Online");
        } else {
            response.setUserStatus("Offline");
        }

        return response;
    }

    @Override
    public UserFindAllResponse findAllUsers() {
        List<UserDto> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserStatus status = userStatusRepository.findByUserId(user.getId());

            UserDto userDto = new UserDto();

            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setFriendIds(user.getFriendIds());
            userDto.setReceivedFriendRequests(user.getReceivedFriendRequests());
            userDto.setSentFriendRequests(user.getSentFriendRequests());

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
            log.warn("friendUser를 찾을 수 없습니다. - friendUserEmail: {}", email);
            throw new IllegalArgumentException("User를 찾을 수 없습니다.");
        }

        if (email.equals(currentUser.getEmail())) {
            log.info("자신의 email과 동일 - friendEmail: {}, userEmail: {}" , email, currentUser.getEmail());
            throw new IllegalStateException("자기 자신에게는 친구 요청을 보낼 수 없습니다.");
        }

        if (currentUser.getSentFriendRequests().contains(friendUser.getId())) {
            log.info("중복된 친구 요청 - userSentFriendRequest: {}, friendEmail: {}",  currentUser.getSentFriendRequests(), email);
            throw new IllegalStateException("이미 처리중인 요청입니다.");
        }

        if (currentUser.getFriendIds().contains(friendUser.getId())) {
            log.info("이미 추가된 친구 - userFriendIds: {}, friendId: {}", currentUser.getFriendIds(), friendUser.getId());
            throw new IllegalStateException("이미 추가된 친구입니다.");
        }

        friendUser.getReceivedFriendRequests().add(userId);
        currentUser.getSentFriendRequests().add(friendUser.getId());

        userRepository.save(friendUser);
        userRepository.save(currentUser);
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
    }

    @Override
    public void rejectFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser = userRepository.findById(friendId);

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
        User friendUser = userRepository.findById(friendId);

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
        User friendUser = userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getFriendIds().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getFriendIds().remove(friendId);
        friendUser.getFriendIds().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }
}
