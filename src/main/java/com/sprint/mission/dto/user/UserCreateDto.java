package com.sprint.mission.dto.user;

import com.sprint.mission.dto.binarycontent.BinaryContentCreateDto;
import com.sprint.mission.entity.BinaryContent;
import com.sprint.mission.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {

    private String userName;
    private String email;
    private String password;
    private BinaryContent profileImage;

}
