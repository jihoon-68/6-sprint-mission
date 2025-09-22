package com.sprint.mission.discodeit.channel.domain;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;

public sealed class ChannelException extends DiscodeitException {

    public ChannelException(String message) {
        super(message);
    }

    public static final class BlankChannelNameException extends ChannelException {

        public BlankChannelNameException() {
            super("Channel name cannot be blank");
        }
    }

    public static final class InsufficientChannelParticipantsException extends ChannelException {

        public InsufficientChannelParticipantsException() {
            super("Channel must have at least 2 participants");
        }
    }

    public static final class PrivateChannelModificationNotAllowedException extends ChannelException {

        public PrivateChannelModificationNotAllowedException() {
            super("Cannot modify private channel");
        }
    }
}
