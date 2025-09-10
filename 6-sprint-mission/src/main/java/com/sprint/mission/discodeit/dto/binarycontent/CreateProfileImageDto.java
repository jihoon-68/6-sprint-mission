package com.sprint.mission.discodeit.dto.binarycontent;

import java.util.UUID;

public record CreateProfileImageDto (
        UUID userId,
        String imagePath
){
}


