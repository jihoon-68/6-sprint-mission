package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserInterface;

import java.util.List;
import java.util.UUID;

public class FileUserInterface implements UserInterface {
    private final UserRepository userRepository = new FileUserRepository();


    @Override
    public void create(String userName) throws DuplicateException {
        if(userRepository.existsByName(userName)) {
            throw new DuplicateException("유저이름: " + userName + " 이(가) 이미 존재합니다.");
        }
        User user = new User(userName);
        userRepository.save(user);
    }

    @Override
    public void rename(UUID userId, String newUserName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
        userRepository.findByName(newUserName)
                .filter(u -> !u.getUuid().equals(userId))
                .ifPresent(u -> {throw new DuplicateException("유저이름: " + newUserName + " 이(가) 이미 존재합니다.");});
        user.setUserName(newUserName);
        userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
        userRepository.remove(user);
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users == null || users.isEmpty()) {
            throw new NotFoundException("유저가 존재하지 않습니다.");
        }
        return users;
    }
}
