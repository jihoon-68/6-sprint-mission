package com.sprint.mission.discodeit.dto.binarycontent;

import java.util.UUID;

public record CreateProfileImage(
    String fileName,
    String contentType,
    byte[] bytes
) {

}


