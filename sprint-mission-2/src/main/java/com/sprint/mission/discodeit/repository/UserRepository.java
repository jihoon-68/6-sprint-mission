package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    //기존 CRUD
    void createUser(User user);
    User findUserById(UUID id);
    User findUserByUserEmail(String userEmail);
    List<User> findAllUsers();
    void updateUser(User user);
    void deleteUser(UUID id);

    /*
    //구현 순위 낮음
    //유저에 서버 추가
    void addUserToChannel(Channel channel, User user);
    void removeUserFromChannel(Channel channel, User user);

    //친구 추가
    void addUserToFriend(User user, UUID id);
    void removeUserFromFriend(User user, UUID id);
     */
}
