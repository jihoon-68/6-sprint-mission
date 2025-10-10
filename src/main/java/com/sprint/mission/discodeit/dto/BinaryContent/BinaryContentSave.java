package com.sprint.mission.discodeit.dto.BinaryContent;

import com.sprint.mission.discodeit.entity.BinaryContent;

public record BinaryContentSave (
        BinaryContent binaryContent,
        byte[] data
){
}
