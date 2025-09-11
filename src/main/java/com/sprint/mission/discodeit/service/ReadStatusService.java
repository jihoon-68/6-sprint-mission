package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusDto;

import java.util.Set;
import java.util.UUID;

public interface ReadStatusService {

    ReadStatusDto.Response create(ReadStatusDto.Request request);

    ReadStatusDto.Response read(UUID id);

    Set<ReadStatusDto.Response> readAll(UUID userId);

    ReadStatusDto.Response update(UUID id, ReadStatusDto.Request request);

    void delete(UUID id);
}
