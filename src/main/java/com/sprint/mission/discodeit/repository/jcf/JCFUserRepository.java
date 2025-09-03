package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    Map<UUID, User> users = new HashMap<>();


    @Override
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User readUser(UUID userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(UUID id) {
        users.remove(id);
    }

    @Override
    public void updateUser(User user) {
        if(users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
        }
        else{
            throw new IllegalArgumentException("User does not exist");
        }
    }


    @Override
    public List<User> readAllUser(){
        return new ArrayList<>(users.values());
    }
}
