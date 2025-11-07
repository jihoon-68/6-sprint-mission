package com.sprint.mission.discodeit.exception.message;

import com.sprint.mission.discodeit.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Map;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageListNotFoundException extends MessageException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MessageListNotFoundException(UUID channelId) {
        super(
                ErrorCode.MESSAGE_NOT_FOUND,
                "채널에 메시지가 하나도 없습니다. channelId=" + channelId,
                Map.of("channelId", channelId)
        );
    }
}
