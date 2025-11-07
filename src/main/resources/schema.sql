CREATE TABLE user (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    updated_at timestamptz,
    username varchar(50) UNIQUE NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    password varchar(60) NOT NULL,
    profile_id uuid,
    CONSTRAINT fk_profile_id FOREIGN KEY (profile_id) REFERENCES binary_contents(id) ON DELETE SET NULL
);

CREATE TABLE binary_content (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    file_name varchar(255) NOT NULL,
    size bigint NOT NULL,
    content_type varchar(100) NOT NULL,
    bytes bytea NOT NULL
);

CREATE TABLE channel (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    updated_at timestamptz,
    name varchar(100),
    description varchar(500),
    type varchar(10) CHECK (type IN ('PUBLIC', 'PRIVATE')) NOT NULL
);

CREATE TABLE message (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    updated_at timestamptz,
    content text,
    channel_id uuid NOT NULL,
    author_id uuid,
    CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE user_status (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    updated_at timestamptz,
    user_id uuid UNIQUE NOT NULL,
    last_active_at timestamptz NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE read_status (
    id uuid PRIMARY KEY,
    created_at timestamptz NOT NULL,
    updated_at timestamptz,
    user_id uuid,
    channel_id uuid,
    last_read_at timestamptz NOT NULL,
    CONSTRAINT unique_user_channel UNIQUE (user_id, channel_id),
    CONSTRAINT fk_read_status_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_read_status_channel FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE
);

CREATE TABLE message_attachment (
    message_id uuid NOT NULL,
    attachment_id uuid NOT NULL,
    PRIMARY KEY (message_id, attachment_id),
    CONSTRAINT fk_message_id FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
    CONSTRAINT fk_attachment_id FOREIGN KEY (attachment_id) REFERENCES binary_contents(id) ON DELETE CASCADE
);