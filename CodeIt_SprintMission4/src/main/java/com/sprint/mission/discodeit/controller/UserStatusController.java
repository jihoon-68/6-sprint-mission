package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user-statuses")
public class UserStatusController {
    private final UserStatusService userStatusService;

    public UserStatusController(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserStatus createUserStatus(@RequestBody UserStatusCreateRequest request) {
        return userStatusService.create(request);
    }

    @RequestMapping(value = "/{userStatusId}", method = RequestMethod.PATCH)
    public UserStatus updateUserStatus(@PathVariable UUID userStatusId, @RequestBody UserStatusUpdateRequest request) {
        return userStatusService.update(userStatusId, request);
    }
}
