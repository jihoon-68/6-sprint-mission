package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user);                    // 생성/갱신(Upsert)
    User findById(UUID id);                // 없으면 null
    List<User> findAll();
    boolean deleteById(UUID id);
    boolean existsById(UUID id);
    long count();
}