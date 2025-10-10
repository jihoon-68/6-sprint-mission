package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userStatus LEFT JOIN FETCH u.profileImage")
    List<User> findAllWithStatusAndProfile();

    void delete(User user);
    void clear();
}
