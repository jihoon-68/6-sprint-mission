package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileReadStatusRepository implements ReadStatusRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","ReadStatusData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(readStatus.getId());
        FileInitSaveLoad.<ReadStatus>save(filePath, readStatus);

        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return findAll()
                .stream()
                .filter(readStatus -> readStatus.getId().equals(id))
                .findAny();
    }

    @Override
    public List<ReadStatus> findAll() {
        return FileInitSaveLoad.<ReadStatus>load(directory);
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
