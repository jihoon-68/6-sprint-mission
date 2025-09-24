package com.sprint.mission.discodeit.DTO.User;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "User 생성 정보")
public record CreateUserDTO(
        String username,
        String email,
        String password
){}
