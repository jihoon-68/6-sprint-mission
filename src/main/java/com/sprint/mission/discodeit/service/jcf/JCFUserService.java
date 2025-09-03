package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    private JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> readId(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<User> readUsername(String username) {
        return data.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> readEmail(String email) {
        return data.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean update(UUID id, String username, String email, String password) {
        User user = data.get(id);
        if (user == null) {
            return false;
        }

        user.update(username, email, password);
        return true;
    }

    @Override
    public boolean delete(UUID id) { return data.remove(id) != null; }
}