package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userdto.CreateUser;
import com.sprint.mission.discodeit.dto.userdto.UpdateUser;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(CreateUser createUser) {
        User user;
        User findUserByName  = userRepository.findByUsername(createUser.username()).orElse(null);
        User findUserByEmail = userRepository.findAll().stream().filter(users->users.getEmail().equals(createUser.email())).findAny().orElse(null);
        if (findUserByName != null || findUserByEmail != null) {
            throw new IllegalArgumentException("유저네임 혹은 이메일이 같은 유저가 존재합니다.");
        }
        BinaryContent binaryContent = createUser.toBinaryContent();
        user = createUser.toEntity(binaryContent);
        binaryContentRepository.save(binaryContent);
        userStatusRepository.save(UserStatus.fromUser(user.getId(), Instant.now()));
        return userRepository.save(user);
    }

    @Override
    public User find(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(UUID userId, UpdateUser updateUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(updateUser.newUsername(), updateUser.newEmail(), updateUser.newPassword());
        user.update(updateUser.online());
        return userRepository.save(user);
    }

    @Override
    public User updateState(UUID userId, boolean online){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(online);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        // 유저의 프로필 사진들 객체 삭제
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("UserService delete: userId 없음"));
        List<BinaryContent> binaryContentsInUser = binaryContentRepository.findAll().stream().filter(binaryContent -> binaryContent.getId().equals(user.getProfileId())).toList();
        for(BinaryContent binaryContent: binaryContentsInUser){
            binaryContentRepository.deleteById(binaryContent.getId());
        }
        // 유저 상태들 삭제
        List<UserStatus> userStatusesInUser = userStatusRepository.findAll().stream().filter(userStatus -> userStatus.getUserId().equals(userId)).toList();
        for(UserStatus userStatus: userStatusesInUser){
            userStatusRepository.deleteById(userStatus.getId());
        }
        // 유저 id로 삭제
        userRepository.deleteById(userId);
    }
}
