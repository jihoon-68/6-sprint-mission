package com.sprint.mission.discodeit.message;

import com.sprint.mission.discodeit.message.MessageDto.Request;
import com.sprint.mission.discodeit.message.domain.Message;

public final class MessageMapper {

    private MessageMapper() {
    }

    public static Message from(Request request) {
        return Message.of(request.content(), request.channelId(), request.authorId());
    }

    public static MessageDto.Response toResponse(Message message) {
        return new MessageDto.Response(
                message.getId(),
                message.getCreatedAt(),
                message.getUpdatedAt(),
                message.getContent(),
                message.getChannelId(),
                message.getAuthorId()
        );
    }
}
