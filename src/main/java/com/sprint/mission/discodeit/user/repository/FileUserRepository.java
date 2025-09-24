package com.sprint.mission.discodeit.user.repository;

import com.sprint.mission.discodeit.common.persistence.JavaSerializationUtils;
import com.sprint.mission.discodeit.user.domain.User;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileUserRepository extends AbstractUserRepository {

    private final Path dataPath;

    public FileUserRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("user.ser");
    }

    @Override
    protected Map<UUID, User> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, User.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
