package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.*;

public interface UserService {
    User create(String name, String password, String nickname, String activeType, String description, List<String> badges);
    User findById(UUID id);
    List<User> findAll();
    User update(UUID id, String name, String nickname);
    boolean delete(UUID id);
}