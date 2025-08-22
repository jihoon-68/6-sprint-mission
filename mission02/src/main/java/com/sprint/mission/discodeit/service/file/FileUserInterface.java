package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserInterface extends SaveAndLoadCommon implements UserService, Serializable {
    Path path = Paths.get(System.getProperty("user.dir"), "users");
    public FileUserInterface() {
        init(path);
    }

    @Override
    public void addUser(User user) {
        List<User> users = load(path);
        if (users.stream().anyMatch(u -> u.getUserName().equals(user.getUserName()))) {
            throw new DuplicateException("사용자 이름이 이미 존재합니다.");
        }
        Path filePath = path.resolve(user.getUuid().toString().concat(".ser"));
        save(filePath, user);
    }
    @Override
    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보를 입력해주십시오");
        }
        try{
            if(Files.exists(path)){
                Files.delete(path.resolve(user.getUuid().toString().concat(".ser")));
                System.out.println("사용자 삭제 완료: " + user.getUserName());
            } else {
                throw new NotFoundException("사용자가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
    }
    @Override
    public void updateUser(String userName, String newUserName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("사용자 이름을 입력해주십시오");
        }
        List<User> users = load(path);
        if(users.stream().noneMatch(u -> u.getUserName().equals(userName))) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
        User user = users.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
        if(newUserName == null || newUserName.isEmpty()) {
            throw new IllegalArgumentException("새로운 사용자 이름을 입력해주십시오");
        }
        if(users.stream().anyMatch(u -> u.getUserName().equals(newUserName)))
            throw new NotFoundException("사용자 이름이 이미 존재합니다.");
        users.stream()
                .filter(u -> u.getUserName().equals(userName))
                .findFirst()
                .ifPresent(u -> u.setUserName(newUserName));

        users.forEach(this::addUser);
        System.out.println("사용자 이름 변경 완료: " + userName + " -> " + newUserName);

    }

    @Override
    public User findUserByName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("사용자 이름을 입력해주십시오");
        }
        List<User> users = load(path);
        return users.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    @Override
    public User findUserById(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID를 입력해주십시오");
        }
        List<User> users = load(path);
        return users.stream()
                .filter(user -> user.getUuid().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = load(path);
        if (users == null || users.isEmpty()) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
        return new ArrayList<>(users);
    }


}
