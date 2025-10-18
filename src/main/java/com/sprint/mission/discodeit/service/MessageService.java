package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.message.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

  Message create(CreateMessageRequest createMessageRequest, List<MultipartFile> attachments);

  Message find(UUID messageId);

  Page<Message> findAllByChannelId(UUID channelId, Pageable pageable);

  Message update(UUID messageId, UpdateMessageRequest updateMessageRequest);

  void delete(UUID messageId);
}
