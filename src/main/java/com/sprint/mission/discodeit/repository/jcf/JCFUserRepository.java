package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {

    private final List<User> data = new ArrayList<>();

    @Override
    public void save(User user) {
        data.removeIf(u -> u.getId().equals(user.getId()));
        data.add(user);
    }

    // 유저 단건 조회
    @Override
    public Optional<User> findById(UUID id) {
        return data.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return data.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return data.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    // 유저 전체 조회
    @Override
    public List<User> findAll() {
        return List.copyOf(data);
    }

    @Override
    public void delete(User user) {
        boolean removed = data.removeIf(u -> u.getId().equals(user.getId()));
        if (!removed) {
            throw new NotFoundException("존재하지 않는 유저입니다. id=" + user.getId());
        }
    }

    @Override
    public void clear(){
        data.clear();
    }

}
