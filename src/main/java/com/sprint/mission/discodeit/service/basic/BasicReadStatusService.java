package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.*;

@Service
public class BasicReadStatusService implements ReadStatusService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final ReadStatusMapper readStatusMapper;

    public BasicReadStatusService(
            UserRepository userRepository,
            ChannelRepository channelRepository,
            ReadStatusRepository readStatusRepository,
            ReadStatusMapper readStatusMapper
    ) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.readStatusRepository = readStatusRepository;
        this.readStatusMapper = readStatusMapper;
    }

    @Override
    public ReadStatusDto.Response create(ReadStatusDto.Request request) {
        userRepository.find(request.userId()).orElseThrow(() ->
                new IllegalArgumentException(READ_STATUS_NOT_FOUND_BY_USER_ID.formatted(request.userId())));
        channelRepository.find(request.channelId()).orElseThrow(() ->
                new IllegalArgumentException(READ_STATUS_NOT_FOUND_BY_CHANNEL_ID.formatted(request.channelId())));
        ReadStatus readStatus = readStatusMapper.from(request, Instant.MIN);
        readStatus = readStatusRepository.save(readStatus);
        return readStatusMapper.toResponse(readStatus);
    }

    @Override
    public ReadStatusDto.Response read(UUID id) {
        ReadStatus readStatus = readStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(READ_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        return readStatusMapper.toResponse(readStatus);
    }

    @Override
    public Set<ReadStatusDto.Response> readAll(UUID userId) {
        return readStatusRepository.findAll(userId)
                .stream()
                .map(readStatusMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public ReadStatusDto.Response update(UUID id, ReadStatusDto.Request request) {
        ReadStatus readStatus = readStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(READ_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        readStatus = readStatusMapper.update(readStatus.getLastReadAt(), readStatus);
        readStatus = readStatusRepository.save(readStatus);
        return readStatusMapper.toResponse(readStatus);
    }

    @Override
    public void delete(UUID id) {
        ReadStatus readStatus = readStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(READ_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        readStatusRepository.delete(readStatus.getId());
    }
}
