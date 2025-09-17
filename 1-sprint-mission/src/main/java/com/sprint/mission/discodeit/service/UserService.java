package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.User.UserFind;
import com.sprint.mission.discodeit.DTOs.User.UserInfo;
import com.sprint.mission.discodeit.DTOs.User.UserUpdate;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(UserInfo info);
    UserFind find(UUID userId);
    List<UserFind> findAll();
    User update(UserUpdate update);
    void delete(UUID userId);
}
