package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
//@Transactional(readOnly = true)     // lazy 로딩된 연관 엔티티를 조회하기 위해 readOnly = true 설정
public class MessageMapper {

  private final BinaryContentMapper binaryContentMapper;
  private final UserMapper userMapper;

  public MessageDto toDto(Message message) {
    return new MessageDto(
        message.getId(),
        message.getCreatedAt(),
        message.getUpdatedAt(),
        message.getContent(),
        message.getChannel().getId(),
        userMapper.toDto(message.getAuthor()),
        message.getAttachments().stream()
            .map(binaryContentMapper::toDto)
            .toList()
    );
  }

  public List<MessageDto> toDtoList(List<Message> messageList) {
    return messageList.stream()
        .map(this::toDto)
        .toList();
  }
}
