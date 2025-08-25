package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    void remove(User user);

    Optional<User> findByName(String userName);

    Optional<User> findById(UUID userId);

    List<User> findAll();

    boolean existsByName(String userName);

}
