package com.sprint.mission.discodeit.dto.binarycontent;

import java.util.UUID;

public record CreateProfileImage(
        UUID userId,
        byte[] bytes
){
}


