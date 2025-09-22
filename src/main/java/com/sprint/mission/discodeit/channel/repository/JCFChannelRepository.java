package com.sprint.mission.discodeit.channel.repository;

import com.sprint.mission.discodeit.channel.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class JCFChannelRepository extends AbstractChannelRepository {

    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected Map<UUID, Channel> getData() {
        return data;
    }
}
