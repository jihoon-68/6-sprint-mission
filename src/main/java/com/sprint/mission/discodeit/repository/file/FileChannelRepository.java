package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.AbstractChannelRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Repository
public class FileChannelRepository extends AbstractChannelRepository {

    private final Path dataPath;

    public FileChannelRepository(Path basePath) {
        super();
        dataPath = basePath.resolve("channel.ser");
    }

    @Override
    protected Map<UUID, Channel> getData() {
        return JavaSerializationUtils.readMap(dataPath, UUID.class, Channel.class);
    }

    @Override
    protected void flush(Map<?, ?> data) {
        JavaSerializationUtils.writeMap(dataPath, data);
    }
}
