package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.Message.MessageDto;
import com.sprint.mission.discodeit.dto.Page.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto create(List<MultipartFile> multipartFiles, MessageCreateRequest messageCreateRequest);
    PageResponse<MessageDto> findAllByChannelId(UUID channelId, Instant cursor , Pageable pageable);
    MessageDto update(UUID messageId, String newContent);
    void delete(UUID id);
}
