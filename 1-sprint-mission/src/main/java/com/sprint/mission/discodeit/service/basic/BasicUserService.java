package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class BasicUserService implements UserService {

    private final UserRepository repository;

    public BasicUserService(UserRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    @Override
    public User create(String name,
                       String password,
                       String nickname,
                       String activeType,
                       String description,
                       List<String> badges) {

        // === 비즈니스 검증 ===
        requireNonBlank(name, "name");
        requireNonBlank(password, "password");
        if (password.length() < 6) {
            throw new IllegalArgumentException("password must be at least 6 chars");
        }

        User user = new User(name, password, nickname, description, activeType, badges);

        return repository.save(user);
    }

    @Override
    public User findById(UUID id) {
        User u = repository.findById(id);
        if (u == null) {
            throw new NoSuchElementException("User not found: " + id);
        }
        return u;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User update(UUID id, String name, String nickname) {
        User u = repository.findById(id);
        if (u == null) {
            throw new NoSuchElementException("User not found: " + id);
        }

        if (name != null && !name.isBlank()) {
            u.update(name, nickname != null && !nickname.isBlank() ? nickname : u.getNickname());
        } else if (nickname != null && !nickname.isBlank()) {
            u.update(u.getName(), nickname);
        } else {
            return u;
        }

        return repository.save(u); // 최종 저장
    }

    @Override
    public boolean delete(UUID id) {
        return repository.deleteById(id);
    }

    private void requireNonBlank(String v, String field) {
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
    }

     private void validateActiveType(String activeType) {
         Set<String> allowed = Set.of("online", "offline", "away");
         if (activeType == null || !allowed.contains(activeType.toLowerCase())) {
             throw new IllegalArgumentException("invalid activeType: " + activeType);
         }
     }
}