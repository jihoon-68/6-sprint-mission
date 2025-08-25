package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {


    void save(Channel channel);

    void remove(Channel channelId);

    List<Channel> findAll();

    Optional<Channel> findById(UUID id);

    Optional<Channel> findByName(String channelName);

    boolean existsByName(String channelName);

}
