package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface UserService {
    User read(String name);
    User create(String name);
    List<User> allRead();
    void modify(String name);
    void delete(String name);
}
