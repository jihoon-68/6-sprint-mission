package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> userMap;

    public JCFUserRepository() {
        this.userMap = new HashMap<>();
    }

    @Override
    public User save(User user) {
        this.userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(this.userMap.get(id));
    }

    @Override
    public List<User> findAll() {
        return this.userMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.userMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.userMap.remove(id);
    }
}
