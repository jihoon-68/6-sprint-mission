package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class JCFUserRepository implements UserRepository {

    private List<User> data = new ArrayList<>();

    @Override
    public void save(User user) {
        data.add(user);
    }

    // 유저 단건 조회
    @Override
    public User findByUserName(String userName) {
        for (User user : data) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        System.out.println("해당 유저가 존재하지 않습니다.");
        return null;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : data) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        System.out.println("해당 유저가 존재하지 않습니다.");
        return null;
    }

    // 유저 전체 조회
    @Override
    public List<User> findAll() {
        if (data.isEmpty()) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
        return data;
    }

    @Override
    public void delete(User user) {
        data.remove(user);
    }
}
