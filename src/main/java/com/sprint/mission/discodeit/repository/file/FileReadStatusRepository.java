package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileReadStatusRepository implements ReadStatusRepository {
    private final Path directory = Paths.get("./src/main/resources/ReadStatus");
    private final FileEdit instance = new FileEdit();

    private Path filePaths(UUID id) {return directory.resolve(id + ".ser");}

    @Override
    public ReadStatus save(ReadStatus ReadStatus) {
        instance.save(directory,ReadStatus.getId(), ReadStatus);
        return ReadStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return instance.load(directory,id);
    }

    @Override
    public List<ReadStatus> findAll() {
        return List.copyOf(instance.loadAll(directory));
    }

    @Override
    public boolean existsById(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public void delete(UUID id) {
        instance.delete(directory,id);
    }
}
