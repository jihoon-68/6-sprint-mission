package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    User create(String name, String mail, String nickname);

    Optional<User> read(UUID id);

    Set<User> readAll();

    User updateMail(UUID id, String newMail);

    User updateNickname(UUID id, String newNickname);

    void delete(UUID id);
}
