package com.sprint.mission.repository;

import com.sprint.mission.dto.channel.ChannelCreateDto;
import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {

    Channel save(ChannelCreateDto channelCreateDto);
    Optional<Channel> findById(UUID id);
    List<Channel> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
