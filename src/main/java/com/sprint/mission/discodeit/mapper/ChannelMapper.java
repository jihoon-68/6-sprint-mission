package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserMapper userMapper;

  public ChannelDto toDto(Channel channel) {
    return new ChannelDto(
        channel.getId(),
        channel.getType(),
        channel.getName(),
        channel.getDescription(),
        readStatusRepository.findAllByChannel_Id(channel.getId()).stream()
            .map(readStatus -> userMapper.toDto(readStatus.getUser()))
            .toList(),
        messageRepository.findAllByChannel_Id(channel.getId()).stream()
            .map(BaseUpdatableEntity::getUpdatedAt)
            .max(Comparator.naturalOrder())
            .orElse(channel.getCreatedAt())
    );
  }

}
