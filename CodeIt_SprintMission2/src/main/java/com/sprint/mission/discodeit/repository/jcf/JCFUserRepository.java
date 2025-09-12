package com.sprint.mission.discodeit.repository.jcf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> data = new HashMap<>();

    @Override
    public User createUser(String name, String email) {
        User user = new User(name, email);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(UUID id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            System.out.println("해당 ID에 맞는 유저가 없습니다.");
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User updateUser(UUID id, String newName, String newEmail) {
        User user = data.get(id);
        user.updateUser(newName, newEmail);
        return user;
    }

    @Override
    public boolean deleteUser(UUID id) {
        return data.remove(id) != null;
    }

}
