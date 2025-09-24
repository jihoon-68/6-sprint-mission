package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userdto.CreateUser;
import com.sprint.mission.discodeit.dto.userdto.UpdateUser;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(CreateUser createUser);
    User find(UUID userId);
    List<User> findAll();
    User update(UUID userId, UpdateUser updateUser);
    User updateState(UUID userId, boolean online);
    void delete(UUID userId);
}
