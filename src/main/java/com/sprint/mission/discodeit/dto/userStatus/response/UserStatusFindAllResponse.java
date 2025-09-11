package com.sprint.mission.discodeit.dto.userStatus.response;

import com.sprint.mission.discodeit.dto.userStatus.model.UserStatusDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserStatusFindAllResponse {
    List<UserStatusDto> userStatusDtos;
}
