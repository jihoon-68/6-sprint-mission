package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String email, String password, String username) {
        if (email.contains(" ")) {
            System.out.println("[Error] 이메일에 공백을 포함할 수 없습니다.");
            return;
        }

        if (password.length() < 6) {
            System.out.println("[Error] 비밀번호는 6글자 이상이여야 합니다.");
            return;
        }

        if (!userRepository.existsByEmail(email)) {
            User user = new User(email, username, password);

            userRepository.save(user);
            System.out.println("[Info] 유저가 생성되었습니다.");
        } else {
            System.out.println("[Error] 이미 사용중인 이메일 입니다.");
        }
    }

    @Override
    public void updateUsername(String username, UUID id) {
        if (userRepository.existsById(id)) {
            User updateUser = userRepository.findById(id);
            updateUser.updateUsername(username);

            userRepository.save(updateUser);
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

        if (userRepository.existsById(id)) {
            User updateUser = userRepository.findById(id);
            updateUser.updatePassword(password);
            System.out.println("[Info] 비밀번호가 변경되었습니다.");
        } else {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
        }
    }

    @Override
    public void updateEmail(String email, UUID id) {
        if (email.contains(" ")) {
            System.out.println("[Error] 이메일에 공백을 포함할 수 없습니다.");
            return;
        }

        if (!userRepository.existsById(id)) {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
            return;
        }

        if (userRepository.existsByEmail(email)) {
            System.out.println("[Error] 이미 사용중인 이메일 입니다.");
            return;
        }

        User updateUser = userRepository.findById(id);
        updateUser.updateEmail(email);
        userRepository.save(updateUser);
        System.out.println("[Info] 이메일이 변경되었습니다.");

    }

    @Override
    public void deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println("[Info] 유저가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 유저 삭제에 실패했습니다.");
        }
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("[Error] 잘못된 이메일 또는 비밀번호 입니다.");
            return null;
        }
        return user;
    }

    @Override
    public void signup(String email, String password, String username) {
        createUser(email, password, username);
    }
}
