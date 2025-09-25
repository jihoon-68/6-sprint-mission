package com.sprint.mission.discodeit.exception;

import java.util.UUID;

public class ChannelModificationNotAllowedException extends RuntimeException {
    private final UUID channelId;

    public ChannelModificationNotAllowedException(UUID channelId, String reason) {
        super(reason);
        this.channelId = channelId;
    }

    public static ChannelModificationNotAllowedException forPrivate(UUID channelId) {
        return new ChannelModificationNotAllowedException(channelId, "PRIVATE 채널은 수정할 수 없습니다.");
    }

    public UUID getChannelId() { return channelId; }
}