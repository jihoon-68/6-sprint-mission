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

    @Override
    public void sendFriendRequest(UUID userId, String email) {
        User friendUser = userRepository.findByEmail(email);
        User currentUser = userRepository.findById(userId);

        if (friendUser == null) {
            System.out.println("[Error] 유저를 찾을 수 없습니다.");
            return;
        }

        if (email.equals(currentUser.getEmail())) {
            System.out.println("[Error] 자기 자신은 추가 할 수 없습니다.");
            return;
        }

        if (currentUser.getSentFriendRequests().contains(friendUser.getId())) {
            System.out.println("[Error] 이미 처리중인 요청입니다.");
            return;
        }

        if (currentUser.getFriendIds().contains(friendUser.getId())) {
            System.out.println("[Error] 이미 추가가 완료되었습니다.");
            return;
        }

        friendUser.getReceivedFriendRequests().add(userId);
        currentUser.getSentFriendRequests().add(friendUser.getId());

        userRepository.save(friendUser);
        userRepository.save(currentUser);

        System.out.println("[Info] 친구 요청을 보냈습니다.");
    }

    @Override
    public void acceptFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser = userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getReceivedFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getFriendIds().add(friendId);
        currentUser.getReceivedFriendRequests().remove(friendId);


        friendUser.getFriendIds().add(userId);
        friendUser.getSentFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);

        System.out.println("[Info] 친구 추가가 완료되었습니다.");
    }

    @Override
    public void rejectFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getReceivedFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getReceivedFriendRequests().remove(friendId);
        friendUser.getSentFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }

    @Override
    public void cancelSendFriendRequest(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getSentFriendRequests().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getSentFriendRequests().remove(friendId);
        friendUser.getReceivedFriendRequests().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);
    }

    @Override
    public void deleteFriend(UUID userId, UUID friendId) {
        User currentUser = userRepository.findById(userId);
        User friendUser =  userRepository.findById(friendId);

        if (friendUser == null) {
            currentUser.getFriendIds().remove(friendId);
            userRepository.save(currentUser);
            return;
        }

        currentUser.getFriendIds().remove(friendId);
        friendUser.getFriendIds().remove(userId);

        userRepository.save(currentUser);
        userRepository.save(friendUser);

        System.out.println("[Info] 친구 삭제가 완료되었습니다.");
    }
}
