package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final List<User> users;

    public JCFUserService() {
        this.users = new ArrayList<>();
    }

    @Override
    public void createUser(User user) {
        users.add(user);
        System.out.println("[Info] 유저가 생성되었습니다.");
    }

    @Override
    public void updateUsername(String username, UUID id) {
        User existUser = findUserById(id);

        if (existUser != null) {
            existUser.updateUsername(username);
            System.out.println("[Info] 이름이 변경되었습니다.");
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void updatePassword(String password, UUID id) {
        if (password.length() < 6) {
            System.out.println("[Error] 비밀번호는 6글자 이상이여야 합니다.");
            return;
        }

        User existUser = findUserById(id);

        if (existUser != null) {
            existUser.updatePassword(password);
            System.out.println("[Info] 비밀번호가 변경되었습니다.");
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void  updateEmail(String email, UUID id) {
        if (email.contains(" ")) {
            System.out.println("[Error] 이메일에 공백을 포함할 수 없습니다.");
            return;
        }

        User existUser = findUserById(id);

        if (existUser != null) {
            if (users.stream().noneMatch(u -> u.getEmail().equals(email))) {
                existUser.updateEmail(email);
                System.out.println("[Info] 유저 업데이트가 완료되었습니다.");
            } else {
                System.out.println("[Error] 이미 사용중인 이메일 입니다.");
            }
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = findUserById(id);
        if (user != null) {
            users.remove(user);
            System.out.println("[Info] 유저가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 유저 삭제에 실패했습니다.");
        }
    }

    @Override
    public User findUserById(UUID id) {
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public User login(String email, String password) {
        if (users.stream().noneMatch(u -> u.getEmail().equals(email) && u.getPassword().equals(password))) {
            System.out.println("[Error] 잘못된 이메일 입니다.");
        } else {
            User user = findUserByEmail(email);

            if (user.getPassword().equals(password)) {
                System.out.println("[Info] " + user.getUsername() + "님 반갑습니다.");
                return user;
            } else if (!user.getPassword().equals(password)) {
                System.out.println("[Error] 잘못된 비밀번호 입니다.");
            } else {
                System.out.println("[Error] 로그인 실패");
            }
        }
        return null;
    }

    @Override
    public void signup(String email, String password, String username) {
        if (email.contains(" ")) {
            System.out.println("[Error] 이메일에 공백을 포함할 수 없습니다.");
            return;
        }

        if (password.length() < 6) {
            System.out.println("[Error] 비밀번호는 6글자 이상이여야 합니다.");
            return;
        }

        if (users.stream().noneMatch(u -> u.getEmail().equals(email))) {
            User user = new User(email, username, password);

            createUser(user);
        } else if (users.stream().anyMatch(u -> u.getEmail().equals(email))) {
            System.out.println("[Error] 이미 사용중인 이메일 입니다.");
        } else {
            System.out.println("[Error] 회원가입에 실패했습니다.");
        }
    }
}
