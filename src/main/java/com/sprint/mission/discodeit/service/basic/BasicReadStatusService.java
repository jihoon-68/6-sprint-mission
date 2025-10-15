package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.ReadStatus.ReadStatusId;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;

import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  @Override
  @Transactional
  public ReadStatus create(ReadStatusCreateRequest request) {
    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.userId()));

    Channel channel = channelRepository.findById(request.channelId())
        .orElseThrow(() -> new EntityNotFoundException("Channel not found with id: " + request.channelId()));

    ReadStatus readStatus = new ReadStatus(
        user,
        channel,
        Instant.now()
    );

    return readStatusRepository.save(readStatus);
  }

  @Override
  public ReadStatus find(UUID userId, UUID channelId) {
    ReadStatusId readStatusId = new ReadStatusId(userId, channelId);
    return readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new EntityNotFoundException("ReadStatus not found for User: " + userId + " and Channel: " + channelId));
  }

  @Override
  public List<ReadStatus> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId);
  }

  @Override
  @Transactional
  public ReadStatus update(UUID userId, UUID channelId, ReadStatusUpdateRequest request) {
    ReadStatusId readStatusId = new ReadStatusId(userId, channelId);
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new EntityNotFoundException("ReadStatus not found for User: " + userId + " and Channel: " + channelId));

      readStatus.update(Instant.now());

    return readStatus;
  }

  @Override
  @Transactional
  public void delete(UUID userId, UUID channelId) {
    ReadStatusId readStatusId = new ReadStatusId(userId, channelId);
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new EntityNotFoundException("ReadStatus not found for User: " + userId + " and Channel: " + channelId));
    readStatusRepository.delete(readStatus);
  }
}