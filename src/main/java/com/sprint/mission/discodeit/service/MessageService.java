package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest.CreateMessageWithContent;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.dto.response.PagedResponse;

import java.util.UUID;

public interface MessageService {

    MessageResponse create(CreateMessageWithContent createRequest);

    MessageResponse find(UUID messageId);

    PagedResponse<MessageResponse> findAllByChannelId(UUID channelId, int page);

    MessageResponse update(UUID messageId, MessageUpdateRequest request);

    void delete(UUID messageId);
}
