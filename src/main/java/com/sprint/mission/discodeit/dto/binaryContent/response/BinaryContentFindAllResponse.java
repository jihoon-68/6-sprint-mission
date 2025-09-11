package com.sprint.mission.discodeit.dto.binaryContent.response;

import com.sprint.mission.discodeit.dto.binaryContent.model.BinaryContentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BinaryContentFindAllResponse {
    List<BinaryContentDto> binaryContents;
}
