package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> users;

    public  JCFUserRepository() {
        users = new HashMap<UUID, User>();
    }

    @Override
    public boolean save(User user) {
        if(users.containsKey(user.getId()))
        {
            System.out.println("User already exists");
            return false;
        }

        Optional<User> temp = users.values().stream().filter(u -> u.getEmail().equals(user.getEmail())).findFirst();
        if(temp.isPresent())
        {
            System.out.println("User already exists");
            return false;
        }

        users.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        if(users.containsKey(user.getId()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        users.remove(user.getId());
        return true;
    }

    @Override
    public boolean update(User user) {
        if(users.containsKey(user.getId()) == false)
        {
            System.out.println("User does not exists");
            return false;
        }

        user.updateUpdatedAt();
        users.put(user.getId(),user);
        return true;
    }

    @Override
    public Map<UUID, User> getAllUsers() {
        return users.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
