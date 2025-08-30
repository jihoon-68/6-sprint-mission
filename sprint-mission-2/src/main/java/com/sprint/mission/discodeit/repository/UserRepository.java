package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    //기존 CRUD
    public void createUser(User user);
    public User findUserById(UUID id);
    public User findUserByUserEmail(String userEmail);
    public List<User> findAllUsers();
    public void updateUser(User user);
    public void deleteUser(UUID id);

    /*
    //구현 순위 낮음
    //유저에 서버 추가
    public void addUserToChannel(Channel channel, User user);
    public void removeUserFromChannel(Channel channel, User user);

    //친구 추가
    public void addUserToFriend(User user, UUID id);
    public void removeUserFromFriend(User user, UUID id);
     */
}
