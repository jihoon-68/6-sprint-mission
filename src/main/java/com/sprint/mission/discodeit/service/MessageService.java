package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(List<MultipartFile> multipartFiles, CreateMessageDTO createMessageDTO);
    Message find(UUID id);
    List<Message> findAllByChannelId(UUID channelId);
    Message update(UUID messageId, String newContent);
    void delete(UUID id);
}
