package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user); // create + update
    User findById(UUID id); // read
    List<User> findAll();
    boolean deleteById(UUID id);
    void reset(); // 저장 초기화 (옵션)
}

