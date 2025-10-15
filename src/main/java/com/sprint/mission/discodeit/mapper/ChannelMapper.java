package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserMapper userMapper;

  public ChannelDto toDto(Channel channel) {
    if (channel == null) {
      return null;
    }

    Instant lastMessageAt = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
        .map(Message::getCreatedAt)
        .orElse(Instant.MIN);

    List<UserDto> participantDtos = Collections.emptyList();

    if (channel.getType().equals(ChannelType.PRIVATE)) {
      participantDtos = readStatusRepository.findAllByChannelId(channel.getId()).stream()
          .map(ReadStatus::getUser)
          .map(userMapper::toDto)
          .toList();
    }

    return new ChannelDto(
        channel.getId(),
        channel.getType(),
        channel.getName(),
        channel.getDescription(),
        participantDtos,
        lastMessageAt
    );
  }
}