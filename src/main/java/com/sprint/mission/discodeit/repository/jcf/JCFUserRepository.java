package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {
    private final List<User> userDeat;

    public  JCFUserRepository() {
        userDeat = new ArrayList<User>();
    }

    public void createUser(User user) {
        userDeat.add(user);
    }

    public User findUserById(UUID id) {
        return userDeat.stream()
                .filter(user-> user.getUserId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User findUserByEmail(String userEmail) {
        return userDeat.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .findAny()
                .orElse(null);
    }

    public List<User> findAllUsers() {
        return userDeat;
    }

    public void updateUser(User user) {
        int idx = userDeat.indexOf(user);
        if (idx == -1) {
            throw new NullPointerException("해당 유저 없음");
        }
        userDeat.set(idx, user);
    }

    public void deleteUser(UUID id) {
        userDeat.remove(findUserById(id));
    }
}
