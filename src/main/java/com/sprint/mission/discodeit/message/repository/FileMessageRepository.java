package com.sprint.mission.discodeit.message.repository;

import com.sprint.mission.discodeit.common.persistence.JavaSerializationUtils;
import com.sprint.mission.discodeit.message.domain.Message;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileMessageRepository extends AbstractMessageRepository {

    private final Path dataPath;

    public FileMessageRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("message.ser");
    }

    @Override
    protected Map<UUID, Message> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, Message.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
