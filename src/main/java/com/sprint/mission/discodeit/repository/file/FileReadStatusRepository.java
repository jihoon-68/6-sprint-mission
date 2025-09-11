package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.AbstractReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileReadStatusRepository extends AbstractReadStatusRepository {

    private final Path dataPath;

    public FileReadStatusRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("read-status.ser");
    }

    @Override
    protected Map<UUID, ReadStatus> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, ReadStatus.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
