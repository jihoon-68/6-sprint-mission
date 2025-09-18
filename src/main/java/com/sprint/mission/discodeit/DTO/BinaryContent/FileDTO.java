package com.sprint.mission.discodeit.DTO.BinaryContent;

import java.io.File;

public record FileDTO (
        String FileName,
        String savedName,
        File file               // 저장 경로
){
}
