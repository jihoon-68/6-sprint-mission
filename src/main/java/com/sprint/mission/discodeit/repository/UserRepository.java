package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    Optional<User> findById(UUID id);
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.userStatus " +
            "LEFT JOIN FETCH u.profile " +
            "WHERE u.username = :username")
    Optional<User> findByUsernameWithStatusAndProfile(@Param("username") String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userStatus LEFT JOIN FETCH u.profile")
    List<User> findAllWithStatusAndProfile();

    void delete(User user);
    // void clear();
}
