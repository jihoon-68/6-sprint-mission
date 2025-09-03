package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String name, String mail, String nickname) {
        return userRepository.save(User.of(name, mail, nickname));
    }

    @Override
    public Optional<User> read(UUID id) {
        return userRepository.find(id);
    }

    @Override
    public Set<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateMail(UUID id, String newMail) {
        User user = userRepository.find(id).orElseThrow();
        return userRepository.save(user.updateMail(newMail));
    }

    @Override
    public User updateNickname(UUID id, String newNickname) {
        User user = userRepository.find(id).orElseThrow();
        return userRepository.save(user.updateName(newNickname));
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(id);
    }
}
