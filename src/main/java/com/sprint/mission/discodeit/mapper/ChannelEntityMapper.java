package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.ChannelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelEntityMapper {

  ChannelDTO.Channel toChannel(ChannelEntity channelEntity);

  ChannelEntity toEntity(ChannelDTO.Channel channelDTO);

}
