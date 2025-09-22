package com.sprint.mission.discodeit.channel.repository;

import com.sprint.mission.discodeit.channel.domain.Channel;
import com.sprint.mission.discodeit.common.persistence.JavaSerializationUtils;
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
