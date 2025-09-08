package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.abstractservice.AbstractUserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFUserService extends AbstractUserService {

    public JCFUserService(UserRepository repo)
    {
        super(repo);
    }
}
