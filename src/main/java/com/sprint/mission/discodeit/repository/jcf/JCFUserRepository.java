package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {

    private final Map<String, User> users;

    public  JCFUserRepository() {
        users = new HashMap<String, User>();
    }

    @Override
    public boolean save(User user) {
        if(users.containsKey(user.getEmail()))
        {
            System.out.println("User already exists");
            return false;
        }

        users.put(user.getEmail(), user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        if(users.containsKey(user.getEmail()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        users.remove(user.getEmail());
        return true;
    }

    @Override
    public boolean update(User user) {
        if(users.containsKey(user.getEmail()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        user.updateUpdatedAt();
        users.put(user.getEmail(),user);
        return true;
    }

    @Override
    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }
}
