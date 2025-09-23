package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.messagedto.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

  Message create(CreateMessageRequest createMessageRequest);

  Message find(UUID messageId);

  List<Message> findAllByChannelId(UUID channelId);

  Message update(UUID messageId, UpdateMessageRequest updateMessageRequest);

  void delete(UUID messageId);
}
