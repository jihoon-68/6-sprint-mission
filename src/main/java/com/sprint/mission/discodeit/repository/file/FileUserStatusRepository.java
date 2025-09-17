package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "file"
)
public class FileUserStatusRepository implements UserStatusRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","UserStatusData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(userStatus.getId());
        FileInitSaveLoad.<UserStatus>save(filePath, userStatus);

        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return findAll()
                .stream()
                .filter(readStatus -> readStatus.getId().equals(id))
                .findAny();
    }

    @Override
    public List<UserStatus> findAll() {
        return FileInitSaveLoad.<UserStatus>load(directory);
    }

    @Override
    public boolean existsById(UUID id) {
        Path path = resolvePath(id);
        return Files.exists(path);
    }

    @Override
    public void deleteById(UUID id) {
        Path path = resolvePath(id);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
