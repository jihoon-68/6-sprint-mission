package com.sprint.mission.repository;

import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {

    User save(UserCreateDto userCreateDto);
    Optional<User> findById(UUID id);
    Optional<User> findByUserName(String userName);
    List<User> findAll();
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    void deleteById(UUID id);

}
