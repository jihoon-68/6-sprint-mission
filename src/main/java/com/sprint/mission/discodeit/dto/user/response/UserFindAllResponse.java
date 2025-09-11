package com.sprint.mission.discodeit.dto.user.response;

import com.sprint.mission.discodeit.dto.user.model.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserFindAllResponse {
    List<UserDto> users;
}
