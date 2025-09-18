package com.sprint.mission.discodeit.repository.file;


import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
@Repository
public class FileUserStatusRepository implements UserStatusRepository {
    private final Path directory = Paths.get("./src/main/resources/UserStatus");
    private final FileEdit instance = new FileEdit();

    FileUserStatusRepository() {instance.init(directory);}

    @Override
    public UserStatus save(UserStatus userStatus) {
        instance.save(directory,userStatus.getId(),userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return instance.load(directory,id);
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        List<UserStatus> userStatuses = findAll();
        return userStatuses.stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        return instance.loadAll(directory);
    }

    @Override
    public boolean existsById(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        instance.delete(directory,id);
    }
}
