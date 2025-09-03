package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {

    private final Map<UUID, User> data;

    public JCFUserService() {
        data = new HashMap<>();
    }

    @Override
    public User create(String name, String mail, String nickname) {
        User newUser = User.of(name, mail, nickname);
        data.put(newUser.id(), newUser);
        return newUser;
    }

    @Override
    public Optional<User> read(UUID id) {
        User user = data.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Set<User> readAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public User updateMail(UUID id, String newMail) {
        return data.compute(id,
                (keyId, valueUser) -> Objects.requireNonNull(valueUser).updateMail(newMail));
    }

    @Override
    public User updateNickname(UUID id, String newNickname) {
        return data.compute(id,
                (keyId, valueUser) -> Objects.requireNonNull(valueUser).updateNickname(newNickname));
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
