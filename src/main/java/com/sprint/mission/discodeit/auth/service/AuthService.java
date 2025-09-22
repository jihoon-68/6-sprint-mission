package com.sprint.mission.discodeit.auth.service;


import com.sprint.mission.discodeit.auth.AuthDto.Request;
import com.sprint.mission.discodeit.auth.AuthDto.Response;

public interface AuthService {

    Response login(Request request);
}
