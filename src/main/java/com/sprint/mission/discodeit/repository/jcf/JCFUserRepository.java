package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    Map<UUID, User> users = new HashMap<>();


    @Override
    public void enroll(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findById(UUID userId) {
        return users.get(userId);
    }

    @Override
    public void delete(UUID id) {
        users.remove(id);
    }
    @Override
    public List<User> findAll(){
        return new ArrayList<>(users.values());
    }
}
