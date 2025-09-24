package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "jcf",
        matchIfMissing = true
)
public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> userMap;

    public JCFUserRepository() {
        this.userMap = new HashMap<>();
    }

    @Override
    public User save(User user) {
        log.info("JCF*Repository 실행");

        this.userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(this.userMap.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }


    @Override
    public List<User> findAll() {
        return this.userMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.userMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.userMap.remove(id);
    }
}
