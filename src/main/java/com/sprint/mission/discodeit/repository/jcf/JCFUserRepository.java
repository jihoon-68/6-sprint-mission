package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.AbstractUserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class JCFUserRepository extends AbstractUserRepository {

    private final Map<UUID, User> data;

    public JCFUserRepository() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected Map<UUID, User> getData() {
        return data;
    }
}
