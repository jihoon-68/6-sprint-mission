package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public User createUser(User user) {
        data.put(user.getuserId(), user);
        return user;
    }

    @Override
    public User readUser(UUID id) {
        return data.get(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        if (data.containsKey(id)) {
            data.put(id, updatedUser);
            return updatedUser;
        }
        return null;
    }

    @Override
    public boolean deleteUser(UUID id) {
        return data.remove(id) != null;
    }
}
