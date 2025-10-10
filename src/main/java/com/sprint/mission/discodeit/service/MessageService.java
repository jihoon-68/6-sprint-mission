package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.dto.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.Message.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto create(List<MultipartFile> multipartFiles, MessageCreateRequest messageCreateRequest);
    List<MessageDto> findAllByChannelId(UUID channelId);
    MessageDto update(UUID messageId, String newContent);
    void delete(UUID id);
}
