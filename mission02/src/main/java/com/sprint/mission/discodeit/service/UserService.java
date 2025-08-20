package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFUserInterface;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void addUser(User user);

    void removeUser(User user) throws NotFoundException;

    void updateUser(String userName, String newUserName) throws NotFoundException;

    User findUserByName(String userName) throws NotFoundException;

    User findUserById(UUID userId) throws NotFoundException;

    List<User> findAllUsers();

}
