package com.sprint.mission.repository;

import com.sprint.mission.dto.message.MessageCreateDto;
import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Message save(MessageCreateDto messageCreateDto);
    Optional<Message> findById(UUID id);
    List<Message> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    void deleteByChannelId(UUID channelId);
}
