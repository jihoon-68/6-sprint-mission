package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.abstractservice.AbstractUserService;

import java.util.List;

public class FileUserService extends AbstractUserService {
    public FileUserService(UserRepository repository) {
        super(repository);
    }
}
