package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {
    private static List<User> userDeat;

    public  JCFUserRepository() {
        userDeat = new ArrayList<User>();
    }

    public User createUser(String username, int age, String email) {
        User user = new User(username, age, email);
        userDeat.add(user);
        return user;
    }

    public User findUserById(UUID id) {
        return userDeat.stream()
                .filter(user-> user.getUserId().equals(id))
                .toList()
                .get(0);
    }

    public User findUserByUserEmail(String userEmail) {
        return userDeat.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .toList()
                .get(0);
    }

    public List<User> findAllUsers() {
        return userDeat;
    }

    public void updateUser(User user) {
        int idx = userDeat.indexOf(user);
        if (idx == -1) {
            System.out.println("해당 유저 없음");
            return;
        }
        userDeat.set(idx, user);
    }

    public void deleteUser(UUID id) {
        userDeat.remove(findUserById(id));
    }
}
