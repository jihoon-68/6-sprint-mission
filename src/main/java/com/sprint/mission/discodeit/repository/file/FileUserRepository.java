package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.AbstractUserRepository;
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
