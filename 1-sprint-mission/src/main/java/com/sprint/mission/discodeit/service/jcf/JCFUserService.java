package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final List<User> data = new ArrayList<>();

    @Override
    public User create(String name, String password, String nickname, String activeType, String description, List<String> badges) {
        User user = new User(name, password, nickname, activeType, description, badges);

        data.add(user);
        return user;
    }

    @Override
    public User findById(UUID id) {
        return data.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override public List<User> findAll() {
        return data;
    }

    @Override
    public User update(UUID id, String name, String nickname) {
        var user = findById(id);

        if (user == null) throw new NoSuchElementException("Message not found: " + id);

        user.update(name, nickname);

        return user;
    }

    @Override
    public boolean delete(UUID id) {
        var user = findById(id);

        if (user == null) throw new NoSuchElementException("Message not found: " + id);

        return data.remove(user);
    }
}