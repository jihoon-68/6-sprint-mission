package com.sprint.mission.discodeit.dto.channeldto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record CreateChannel(
        @NotBlank ChannelType type,
        @NotBlank String name,
        String description,
        List<UUID> userIds         // PRIVATE 채널만 필요
){
}
