package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.FindMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(List<MultipartFile> multipartFiles, CreateMessageDTO createMessageDTO);
    FindMessageDTO find(UUID id);
    List<FindMessageDTO> findAllByChannelId(UUID channelId);
    void update(List<MultipartFile> multipartFiles, UpdateMessageDTO updateMessageDTO);
    void delete(UUID id);
}
