package com.sprint.mission.discodeit.service.Basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email) {
        return userRepository.createUser(name, email);
    }

    @Override
    public User getUserById(UUID id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User updateUser(UUID id, String newName, String newEmail) {
        return userRepository.updateUser(id, newName, newEmail);
    }

    @Override
    public boolean deleteUser(UUID id) {
        return userRepository.deleteUser(id);
    }




}
