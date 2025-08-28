package com.sprint.mission.discodeit.service.basic.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserService;

public class BasicFileUserService implements UserService {

    private FileUserRepository userRepository = new FileUserRepository();

    // 유저 생성
    @Override
    public User createUser(String email, String userName, String password) {
        if (userRepository.findByUserName(userName) != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        User user = new User(email, userName, password);
        userRepository.save(user);
        System.out.println("유저 추가 및 저장 완료: " + user.getUserName());
        return user;
    }

    // 이메일 재설정
    @Override
    public void updateEmail(User user, String email) {
        if (userRepository.findByUserName(email) != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        user.setEmail(email);
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user);
        System.out.println("이메일 수정 및 저장 완료 : " + user.getUserName());
    }

    // 이름 재설정
    @Override
    public void updateUserName(User user, String newUserName) {
        if (userRepository.findByUserName(newUserName) != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        user.setUserName(newUserName);
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user);
        System.out.println("이메일 수정 및 저장 완료 : " + user.getUserName());
    }

    // 비밀번호 재설정
    @Override
    public void updatePassword(User user, String oldPassword, String newPassword) {
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        user.setPassword(newPassword);
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user);
        System.out.println("비밀번호 수정 및 저장 완료 : " + user.getUserName());
    }

    // 유저 삭제
    @Override
    public void deleteUser(User user, String password) {
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        userRepository.delete(user);
        System.out.println("유저 삭제 완료");
    }
}
