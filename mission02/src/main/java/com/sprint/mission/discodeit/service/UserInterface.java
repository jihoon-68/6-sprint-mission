package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserInterface {

    void create(String userName);

    void rename(UUID userId, String newUserName);

    void delete(UUID userId);

    User getUserById(UUID userId);

    User getUserByName(String userName);

    List<User> getUsers();
}
