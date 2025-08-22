package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserInterface implements UserService {
    private final List<User> users;

    public JCFUserInterface() {this.users = new ArrayList<>();}

    @Override
    public void addUser(User user) throws DuplicateException {
        if(users.stream().anyMatch(u -> u.getUserName().equals(user.getUserName()))) {
            throw new DuplicateException("사용자 이름이 이미 존재합니다.");
        }
        users.add(user);
    }

    @Override
    public void removeUser(User user) throws NotFoundException {
        if (!users.remove(user))
            throw new NotFoundException("사용자가 존재하지 않습니다.");

    }

    @Override
    public void updateUser(String userName, String newUserName) throws NotFoundException {
        if(userName == null || newUserName == null) {
            throw new IllegalArgumentException("사용자 이름을 입력해주십시오");
        }
        User user = users.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
        if(users.stream().anyMatch(u -> u.getUserName().equals(newUserName)))
            throw new DuplicateException("사용자 이름이 이미 존재합니다.");
        user.setUserName(newUserName);
    }

    @Override
    public User findUserByName(String userName) throws NotFoundException {
        if(userName == null) {
            throw new IllegalArgumentException("사용자 이름을 입력해주십시오");
        }
        return users.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    @Override
    public User findUserById(UUID userId) throws NotFoundException {
        if(userId == null) {
            throw new IllegalArgumentException("사용자 ID를 입력해주십시오");
        }
        return users.stream()
                .filter(user -> user.getUuid().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    @Override
    public List<User> findAllUsers() {
        if(users.isEmpty()) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        } else {
            return new ArrayList<>(users);
        }
    }


}
