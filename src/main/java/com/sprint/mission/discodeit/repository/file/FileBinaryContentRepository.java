package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository implements BinaryContentRepository {
    private final Path directory = Paths.get("./src/main/resources/BinaryContent");
    private final FileEdit instance = new FileEdit();

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        instance.save(directory,binaryContent.getId(),binaryContent);
        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return instance.load(directory,id);
    }

    @Override
    public List<BinaryContent> findAll() {
        return List.copyOf(instance.loadAll(directory));
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
