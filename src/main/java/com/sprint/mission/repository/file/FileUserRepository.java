package com.sprint.mission.repository.file;

import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.User;
import com.sprint.mission.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserRepository extends SaveAndLoadCommon<User> implements UserRepository {

    public FileUserRepository() {
        super(User.class);
    }

    @Override
    public User save(UserCreateDto userCreateDto) {
        User user = new User(userCreateDto.getUserName(), userCreateDto.getEmail(), userCreateDto.getPassword(), userCreateDto.getProfileImage().getId());
        save(user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        if(load(id).isEmpty()) return Optional.empty();
        return load(id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        List<User> users = loadAll();
        return users.stream().filter(user -> user.getUsername().equals(userName)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return loadAll();
    }

    @Override
    public boolean existsById(UUID id) {
        List<User> users = loadAll();
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        List<User> users = loadAll();
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsByUserName(String userName) {
        List<User> users = loadAll();
        return users.stream().anyMatch(user -> user.getUsername().equals(userName));
    }

    @Override
    public void deleteById(UUID id) {
        delete(id);
    }
}
