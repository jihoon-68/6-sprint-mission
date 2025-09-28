package com.sprint.mission.discodeit.dto.BinaryContent;

import java.io.File;

public record FileDTO (
        String FileName,
        String savedName,
        File file
){
}
