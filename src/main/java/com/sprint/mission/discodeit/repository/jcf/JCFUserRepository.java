package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {
    private final List<User> userDate;

    public  JCFUserRepository() {
        userDate = new ArrayList<User>();
    }

    @Override
    public User save(User user) {
        int idx = userDate.indexOf(user);
        if (idx >=0) {
            userDate.set(idx, user);
        }else {
            userDate.add(user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDate.stream()
                .filter(user-> user.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return userDate.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(userDate);
    }

    @Override
    public boolean existsById(UUID id) {
        return userDate.stream()
                .anyMatch(user -> user.getId().equals(id));
    }

    public void deleteById(UUID id) {
        userDate.removeIf(user -> user.getId().equals(id));
    }
}
