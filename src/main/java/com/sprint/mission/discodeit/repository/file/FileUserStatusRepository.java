package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.AbstractUserStatusRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileUserStatusRepository extends AbstractUserStatusRepository {

    private final Path dataPath;

    public FileUserStatusRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("user-status.ser");
    }

    @Override
    protected Map<UUID, UserStatus> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, UserStatus.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
