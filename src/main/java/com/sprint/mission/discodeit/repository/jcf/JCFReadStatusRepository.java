package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.AbstractReadStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class JCFReadStatusRepository extends AbstractReadStatusRepository {

    private final Map<UUID, ReadStatus> data;

    public JCFReadStatusRepository() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected Map<UUID, ReadStatus> getData() {
        return data;
    }
}
