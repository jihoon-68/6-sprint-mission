package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {
    private final List<User> users;

    public JCFUserRepository() {users = new ArrayList<>();}

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return users.stream().filter(u->u.getUserName().equals(userName)).findFirst();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return users.stream().filter(u->u.getUuid().equals(userId)).findFirst();
    }

    @Override
    public List<User> findAll() {
        if(users.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(users);
    }

    @Override
    public boolean existsByName(String userName) {
        return users.stream().anyMatch(u->u.getUserName().equals(userName));
    }
}
