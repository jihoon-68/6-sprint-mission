package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.Message.MessageDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MessageMapper {
    private final BinaryContentMapper binaryContentMapper;
    private final UserMapper userMapper;

    public MessageDto toDto(Message message) {
        UserDto userDto = userMapper.toDto(message.getAuthor());

        List<BinaryContentDto> binaryContentDtoList = message.getAttachmentIds().stream()
                .map(binaryContentMapper::toDto)
                .toList();

        return new MessageDto(
                message.getId(),
                message.getCreatedAt(),
                message.getUpdatedAt(),
                message.getContent(),
                message.getChannel().getId(),
                userDto,
                binaryContentDtoList
        );
    }
}
