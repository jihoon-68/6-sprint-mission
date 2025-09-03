package com.sprint.mission.discodeit.DTOs;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.Optional;

public record UserInfo(
        String username,
        String email,
        String password,
        BinaryContent profileImage
) {
    public Optional<BinaryContent> getProfileImage() {
        return Optional.ofNullable(profileImage);
    }
}