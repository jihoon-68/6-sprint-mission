-- 1. binary_contents 테이블 (외래키 참조되는 테이블 우선 생성)
CREATE TABLE binary_contents (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  file_size BIGINT NOT NULL,
  content_type VARCHAR(100) NOT NULL
);

-- 2. user 테이블
CREATE TABLE users (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  user_name VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  profile_id UUID REFERENCES binary_contents(id) ON DELETE SET NULL
);

-- 3. channels 테이블
CREATE TYPE channel_type AS ENUM ('PUBLIC', 'PRIVATE');
CREATE TABLE channels (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(500),
  type channel_type NOT NULL
);

-- 4. messages 테이블
CREATE TABLE messages (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  text TEXT,
  content TEXT NOT NULL,
  channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
  author_id UUID REFERENCES users(id) ON DELETE SET NULL
);

-- 5. user_statuses 테이블
CREATE TABLE user_statuses (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
  last_active_at TIMESTAMP WITH TIME ZONE NOT NULL
);

-- 6. message_attachments 테이블
CREATE TABLE message_attachments (
  message_id UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
  attachment_id UUID NOT NULL REFERENCES binary_contents(id) ON DELETE CASCADE,
  PRIMARY KEY (message_id, attachment_id)
);

-- 7. read_statuses 테이블
CREATE TABLE read_statuses (
  id UUID PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL,
  updated_at TIMESTAMP WITH TIME ZONE,
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
  UNIQUE (user_id, channel_id),
  last_read_at TIMESTAMP WITH TIME ZONE NOT NULL
);
