package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReadStatusMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "channel.id", target = "channelId")
    ReadStatusResponseDto toDto(ReadStatus readStatus);

//    public static ReadStatusResponseDto toDto(ReadStatus readStatus){
//        return ReadStatusResponseDto.builder()
//                .id(readStatus.getId())
//                .userId(readStatus.getUser().getId())
//                .channelId(readStatus.getChannel().getId())
//                .lastReadAt(readStatus.getLastReadAt())
//                .build();
//    }

}
