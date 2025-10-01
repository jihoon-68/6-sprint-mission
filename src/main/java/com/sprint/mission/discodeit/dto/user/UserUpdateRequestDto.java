package com.sprint.mission.discodeit.dto.user;

public record UserUpdateRequestDto (
    String newEmail,
    String newUsername,
    String newPassword
    // 바꾸고 싶지 않은 값은 null로 받음.
){}
