package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.File;
import java.util.*;

public class FileUserRepository extends AbstractFileRepository<User> implements UserRepository {
    private Map<UUID, User> data;

    public FileUserRepository(String baseDir) {
        super(new File(baseDir, "users.ser"));
        this.data = load();
    }

    @Override public User save(User user) { data.put(user.getId(), user); save(data); return user; }
    @Override public Optional<User> findById(UUID id) { return Optional.ofNullable(data.get(id)); }
    @Override public List<User> findAll() { return new ArrayList<>(data.values()); }
    @Override public boolean deleteById(UUID id) { boolean r = data.remove(id) != null; if (r) save(data); return r; }
}