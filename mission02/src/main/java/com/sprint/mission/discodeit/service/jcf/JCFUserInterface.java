package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserInterface implements UserInterface {
    private final UserRepository userRepository = new JCFUserRepository();

    @Override
    public void create(String userName) {
        User user = new User(userName);
        userRepository.save(user);
    }

    @Override
    public void rename(UUID userId, String newUserName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
        if(userRepository.existsByName(newUserName)) {
            throw new DuplicateException("유저이름: " + newUserName + " 이(가) 이미 존재합니다.");
        }
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
        if(userRepository.findAll().isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(userRepository.findAll());
    }
}
