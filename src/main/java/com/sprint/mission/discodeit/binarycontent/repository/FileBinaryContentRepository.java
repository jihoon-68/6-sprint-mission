package com.sprint.mission.discodeit.binarycontent.repository;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent;
import com.sprint.mission.discodeit.common.persistence.JavaSerializationUtils;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileBinaryContentRepository extends AbstractBinaryContentRepository {

    private final Path dataPath;

    public FileBinaryContentRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("binary-content.ser");
    }

    @Override
    protected Map<UUID, BinaryContent> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, BinaryContent.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
