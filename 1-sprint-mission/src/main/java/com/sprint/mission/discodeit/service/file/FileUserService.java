package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FileUserService implements UserService {

    private UserRepository repository;

    public FileUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(String name, String password, String nickname, String activeType, String description, List<String> badges) {
        var user = new User(name, password, nickname, activeType, description, badges);
        repository.save(user);
        return user;
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User update(UUID id, String name, String nickname) {
        var user = findById(id);

        if (user == null) return null;

        user.update(name, nickname);

        return repository.save(user);
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
