package com.sprint.mission.discodeit.user.service;

import com.sprint.mission.discodeit.user.UserDto.Request;
import com.sprint.mission.discodeit.user.UserDto.Response;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithLastActivatedAt;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithOnline;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    Response createUser(Request request);

    ResponseWithOnline getUserById(UUID id);

    ResponseWithLastActivatedAt getUserByNicknameAndPassword(String nickname, String password);

    Set<ResponseWithOnline> getUsers();

    Response updateUserById(UUID id, Request request);

    ResponseWithLastActivatedAt updateUserById(UUID id, Instant lastActivatedAt);

    void deleteUserById(UUID id);
}
