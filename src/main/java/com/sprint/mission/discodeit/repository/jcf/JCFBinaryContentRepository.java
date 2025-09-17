package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.AbstractBinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class JCFBinaryContentRepository extends AbstractBinaryContentRepository {

    private final Map<UUID, BinaryContent> data;

    public JCFBinaryContentRepository() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected Map<UUID, BinaryContent> getData() {
        return data;
    }
}
