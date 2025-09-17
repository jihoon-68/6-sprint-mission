package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.User.UserFind;
import com.sprint.mission.discodeit.DTOs.User.UserInfo;
import com.sprint.mission.discodeit.DTOs.User.UserUpdate;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.FileStorageException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@Service
public class BasicUserService implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    public BasicUserService(UserRepository userRepository,
                            UserStatusRepository userStatusRepository,
                            BinaryContentRepository binaryContentRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.binaryContentRepository = binaryContentRepository;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    @Override
    public User create(UserInfo info) {
        if (!isValidPassword(info.password())) {
            throw new IllegalArgumentException("비밀번호는 6자 이상이어야 합니다.");
        }

        var allUsers = userRepository.findAll();

        if (allUsers.stream().filter(u -> u.getEmail().equals(info.email())) != null) {
            throw new IllegalStateException("이메일이 존재합니다.");
        }

        if (allUsers.stream().filter(u -> u.getUsername().equals(info.username())) != null) {
            throw new IllegalStateException("아이디가 존재합니다.");
        }

        User user = new User(info.username(), info.email(), info.password());

        var status = new UserStatus(user.getId());
        userStatusRepository.save(status);

        if (info.profileImage().isPresent()) {
            BinaryContent img = null;
            var p = info.profileImage().get();
            img = binaryContentRepository.save(p);
        }

        return user;
    }

    @Override
    public UserFind find(UUID userId) {
        var user = userRepository.findById(userId).orElseThrow();
        var status = userStatusRepository.findByUserId(userId).orElseThrow();
        var image = binaryContentRepository.findByUserId(userId);

        return new UserFind(userId, user.getUsername(), user.getEmail(), status, image);
    }

    @Override
    public List<UserFind> findAll() {
        var users = userRepository.findAll();

        Map<UUID, Optional<UserStatus>> statusMap = new HashMap<>();
        Map<UUID, Optional<BinaryContent>> imageMap = new HashMap<>();

        for (var u : users) {
            statusMap.put(u.getId(), userStatusRepository.findByUserId(u.getId()));
            imageMap.put(u.getId(), binaryContentRepository.findByUserId(u.getId()));
        }

        return users.stream()
                .map(u -> new UserFind(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        statusMap.get(u.getId()).orElseThrow(),
                        imageMap.get(u.getId())
                ))
                .toList();
    }

    @Override
    public User update(UserUpdate update) {
        User user = getUserById(update.userId());

        user.update(update.newUsername(), update.newEmail(), update.newPassword());

        userRepository.save(user);

        if (update.newProfile().isPresent()) {
            binaryContentRepository.save(update.newProfile().get());
        }

        return user;
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }

        boolean statusDeleted = userStatusRepository.deleteByUserId(userId);

        if (!statusDeleted) {
            log.warn("UserStatus missing while deleting user: {}", userId);
        }

        binaryContentRepository.deleteByUserId(userId);

        userRepository.deleteById(userId);
    }

    private User getUserById(UUID userId) {
        return userRepository
                .findById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }
}
