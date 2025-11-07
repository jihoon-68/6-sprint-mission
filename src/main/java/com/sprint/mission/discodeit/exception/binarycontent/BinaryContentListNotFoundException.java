package com.sprint.mission.discodeit.exception.binarycontent;

import com.sprint.mission.discodeit.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BinaryContentListNotFoundException extends BinaryContentException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BinaryContentListNotFoundException(List<UUID> binaryContentIds) {
        super(
                ErrorCode.BINARY_CONTENT_NOT_FOUND,
                "해당 조건을 만족하는 BinaryContent가 하나도 없습니다.",
                Map.of("ids", binaryContentIds)
        );
    }
}
