package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readStatus.model.ReadStatusDto;
import com.sprint.mission.discodeit.dto.readStatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readStatus.response.ReadStatusFindAllByUserIdResponse;

import java.util.UUID;

public interface ReadStatusService {
    void createReadStatus(ReadStatusCreateRequest request);

    ReadStatusDto findOne(UUID id);

    ReadStatusFindAllByUserIdResponse findAllByUserId(UUID userId);

    void deleteOne(UUID id);

    void updateReadAt(UUID channelId,  UUID userId);

    ReadStatusDto findOneByUserIdAndChannelId(UUID userId, UUID channelId);
}
