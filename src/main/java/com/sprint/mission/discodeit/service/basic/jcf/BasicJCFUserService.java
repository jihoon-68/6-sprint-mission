package com.sprint.mission.discodeit.service.basic.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.UserService;

public class BasicJCFUserService implements UserService {

    private final JCFUserRepository userRepository;

    public BasicJCFUserService(JCFUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 등록
    @Override
    public User createUser(String email, String userName, String password) { // 등록
        User user = new User(email, userName, password);
        userRepository.save(user);
        System.out.println("유저 추가 완료: " + user.getUserName());
        return user;
    }

    // 닉네임 수정
    @Override
    public void updateUserName(User user, String newUserName) {
        user.setUserName(newUserName);
        user.setUpdatedAt(System.currentTimeMillis());
        System.out.println("닉네임 수정 완료 : " + user.getUserName());
    }

    // 이메일 수정
    @Override
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        user.setUpdatedAt(System.currentTimeMillis());
        System.out.println("이메일 수정 완료 : " + user.getEmail());
    }

    // 비밀번호 수정
    @Override
    public void updatePassword(User user, String oldPassword, String newPassword) {
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        user.setPassword(newPassword);
        user.setUpdatedAt(System.currentTimeMillis());
        System.out.println("비밀번호 수정 완료");
    }

    // 유저 삭제
    @Override
    public void deleteUser(User user, String password) {
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        userRepository.delete(user);
    }
}
