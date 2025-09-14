package com.sprint.mission.repository;

import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Message save(UserCreateDto userCreateDto);
    Optional<Message> findById(UUID id);
    List<Message> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void deleteByChannelId(UUID channelId);
}
