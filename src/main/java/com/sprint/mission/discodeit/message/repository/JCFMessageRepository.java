package com.sprint.mission.discodeit.message.repository;

import com.sprint.mission.discodeit.message.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class JCFMessageRepository extends AbstractMessageRepository {

    private final Map<UUID, Message> data;

    public JCFMessageRepository() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected Map<UUID, Message> getData() {
        return data;
    }
}
