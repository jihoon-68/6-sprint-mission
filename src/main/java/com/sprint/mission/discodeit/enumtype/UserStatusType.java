package com.sprint.mission.discodeit.enumtype;

public enum UserStatusType {
    ONLINE(true),
    OFFLINE(false);

    private final boolean value;

    UserStatusType(final boolean value) {
        this.value = value;
    }
    public boolean getValue() {
        return this.value;
    }
}
