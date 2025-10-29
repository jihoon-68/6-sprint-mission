CREATE TABLE users
(
    id         uuid primary key,
    created_at timestamptz         not null,
    updated_at timestamptz,
    username   varchar(50) unique  not null,
    email      varchar(100) unique not null,
    password   varchar(60)         not null,
    profile_id uuid                not null,

    CONSTRAINT fk_users_binary_contents
        FOREIGN KEY (profile_id)
            REFERENCES binary_contents (id)
            on DELETE SET NULL
);

CREATE TABLE channels
(
    id          uuid primary key,
    created_at  timestamptz not null,
    updated_at  timestamptz,
    name        varchar(100),
    description varchar(500),
    type        varchar(10) not null

);

CREATE TABLE messages
(
    id         uuid primary key,
    created_at timestamptz not null,
    updated_at timestamptz,
    text       text,
    channel_id uuid        not null,
    author_id  uuid,

    CONSTRAINT fk_messages_channels
        FOREIGN KEY (channel_id)
            REFERENCES channels (id)
            on delete cascade,
    CONSTRAINT fk_messages_users
        FOREIGN KEY (author_id)
            REFERENCES users (id)
            on delete set null
);

CREATE TABLE binary_contents
(
    id           uuid primary key,
    created_at   timestamptz  not null,
    file_name    varchar(255) not null,
    size         BIGINT       not null,
    content_type varchar(100) not null,
    bytes        bytea        not null
);

CREATE TABLE user_statuses
(
    id             uuid primary key,
    created_at     timestamptz not null,
    updated_at     timestamptz,
    user_id        uuid UNIQUE,
    last_active_at timestamptz not null,

    CONSTRAINT fk_user_statuses_users
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            on delete cascade
);

CREATE TABLE read_statuses
(
    id             uuid primary key,
    created_at     timestamptz not null,
    updated_at     timestamptz,
    user_id        uuid,
    channel_id     uuid,
    last_active_at timestamptz not null,

    CONSTRAINT fk_user_statuses_users
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            on delete cascade,
    CONSTRAINT fk_user_statuses_channels
        FOREIGN KEY (channel_id)
            REFERENCES channels (id)
            on delete cascade,
    CONSTRAINT uq_user_statuses_users_channels UNIQUE (user_id, channel_id)
);

CREATE TABLE message_attachments
(
    message_id    uuid,
    attachment_id uuid,

    CONSTRAINT fk_message_attachments_messages
        FOREIGN KEY (message_id)
            REFERENCES messages (id)
            on delete cascade,
    CONSTRAINT fk_message_binary_contents
        FOREIGN KEY (attachment_id)
            REFERENCES binary_contents (id)
            on delete cascade
);



