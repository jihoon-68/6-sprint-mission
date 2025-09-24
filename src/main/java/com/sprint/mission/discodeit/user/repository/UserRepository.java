package com.sprint.mission.discodeit.user.repository;

import com.sprint.mission.discodeit.common.persistence.CrudRepository;
import com.sprint.mission.discodeit.user.domain.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findByNicknameAndPassword(String nickname, String password);
}
