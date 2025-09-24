package com.sprint.mission.discodeit.user.repository;

import com.sprint.mission.discodeit.user.domain.User;
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
