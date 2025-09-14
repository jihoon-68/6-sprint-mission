package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateReadStatusRequest;
import com.sprint.mission.discodeit.dto.ReadStatusResponse;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    public ReadStatusResponse create(CreateReadStatusRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new NoSuchElementException("User not found with id: " + request.userId());
        }
        if (!channelRepository.existsById(request.channelId())) {
            throw new NoSuchElementException("Channel not found with id: " + request.channelId());
        }
        if (readStatusRepository.findByUserIdAndChannelId(request.userId(), request.channelId()).isPresent()) {
            throw new IllegalArgumentException("ReadStatus already exists for the given user and channel");
        }

        ReadStatus readStatus = new ReadStatus(request.userId(), request.channelId());
        readStatusRepository.save(readStatus);
        return ReadStatusResponse.of(readStatus);
    }

    @Override
    public ReadStatusResponse find(UUID readStatusId) {
        return readStatusRepository.findById(readStatusId)
                .map(ReadStatusResponse::of)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found with id: " + readStatusId));
    }

    @Override
    public List<ReadStatusResponse> findAllByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User not found with id: " + userId);
        }
        return readStatusRepository.findByUserId(userId)
                .stream()
                .map(ReadStatusResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ReadStatusResponse update(UUID readStatusId) {
        ReadStatus readStatus = readStatusRepository.findById(readStatusId)
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found with id: " + readStatusId));
        readStatus.update();
        readStatusRepository.save(readStatus);
        return ReadStatusResponse.of(readStatus);
    }

    @Override
    public void delete(UUID readStatusId) {
        if (!readStatusRepository.existsById(readStatusId)) {
            throw new NoSuchElementException("ReadStatus not found with id: " + readStatusId);
        }
        readStatusRepository.deleteById(readStatusId);
    }
}
