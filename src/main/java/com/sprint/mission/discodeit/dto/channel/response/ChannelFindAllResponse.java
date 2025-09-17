package com.sprint.mission.discodeit.dto.channel.response;

import com.sprint.mission.discodeit.dto.channel.model.ChannelDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChannelFindAllResponse {
    List<ChannelDto> channels;
}
