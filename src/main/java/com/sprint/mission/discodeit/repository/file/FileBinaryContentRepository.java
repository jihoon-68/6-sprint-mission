package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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
public class FileBinaryContentRepository implements BinaryContentRepository {

    @Value("${discodeit.repository.file-directory}")
    private String fileDirectory;

    private Path directory;

    @PostConstruct                 // @Value 필드값은 Bean 생성 이후에 채워짐, 인스턴스 필드 초기화 시점을 뒤로 미뤄 NullPointerException 방지
    public void init() {
        this.directory = Paths.get(System.getProperty("user.dir"), fileDirectory, "BinaryContentData");
    }

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
