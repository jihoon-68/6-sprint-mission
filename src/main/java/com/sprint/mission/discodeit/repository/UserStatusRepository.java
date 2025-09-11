package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {
    UserRepository save(UserRepository userRepository);
    Optional<UserRepository> findById(UUID id);
    List<UserRepository> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
