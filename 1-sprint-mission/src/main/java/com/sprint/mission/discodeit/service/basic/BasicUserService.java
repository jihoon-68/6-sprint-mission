package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String username, String email, String password) {
        if(isValidPassword(password)) {
            User user = new User(username, email, password);
            return userRepository.save(user);
        }

        throw new IllegalArgumentException("비밀번호는 6자 이상이어야 합니다.");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    @Override
    public User find(UUID userId) {
        return getUserById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword) {
        User user = getUserById(userId);
        user.update(newUsername, newEmail, newPassword);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    private User getUserById(UUID userId) {
        return userRepository
                .findById(userId).orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }
}
