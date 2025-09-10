package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository implements BinaryContentRepository {

    public final Path directory = Paths.get(System.getProperty("user.dir"), "file-data","BinaryContentData");

    private Path resolvePath(UUID id) {
        return directory.resolve(id + ".ser");
    }

    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        FileInitSaveLoad.init(directory);

        Path filePath = resolvePath(binaryContent.getId());
        FileInitSaveLoad.<BinaryContent>save(filePath, binaryContent);

        return binaryContent;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return findAll()
                .stream()
                .filter(binarycontent -> binarycontent.getId().equals(id))
                .findAny();
    }

    @Override
    public List<BinaryContent> findAll() {
        return FileInitSaveLoad.<BinaryContent>load(directory);
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
