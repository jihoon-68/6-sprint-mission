package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    void save(Channel channel);
    Channel findById(UUID id);
    // Channel findByName(String name); // 채널명에는 중복 금지 요구사항 없음.
    List<Channel> findAll();
    void delete(Channel channel);
    void clear();
}
