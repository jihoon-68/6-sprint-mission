package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readstatus.CreateReadStatusRequest;
import com.sprint.mission.discodeit.dto.readstatus.UpdateReadStatusRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

  public final ReadStatusRepository readStatusRepository;
  public final UserRepository userRepository;
  public final ChannelRepository channelRepository;


  @Override
  public ReadStatus create(CreateReadStatusRequest createReadStatusRequest) {
    User user = userRepository.findById(createReadStatusRequest.userId())
        .orElseThrow(() -> new NotFoundException(
            "유저가 없습니다: " + createReadStatusRequest.userId()));
    Channel channel = channelRepository.findById(createReadStatusRequest.channelId())
        .orElseThrow(() -> new NotFoundException(
            "채널이 없습니다: " + createReadStatusRequest.channelId()));
    ReadStatus readStatus = new ReadStatus(user, channel, createReadStatusRequest.lastReadAt());
    return readStatusRepository.save(readStatus);
  }

  @Override
  public ReadStatus find(UUID readStatusId) {
    return readStatusRepository.findById(readStatusId)
        .orElseThrow(
            () -> new NotFoundException("ReadStatus with id " + readStatusId + " not found"));
  }

  @Override
  public List<ReadStatus> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId);
  }

  @Override
  public ReadStatus update(UUID readStatusId, UpdateReadStatusRequest updateReadStatusRequest) {
    ReadStatus readStatus = readStatusRepository.findById(readStatusId)
        .orElseThrow(() -> new NotFoundException(
            "ReadStatus with id " + readStatusId + " not found"));
    readStatus.update(updateReadStatusRequest.newLastReadAt());
    return readStatusRepository.save(readStatus);
  }

  @Override
  public void delete(UUID readStatusId) {
    if (!readStatusRepository.existsById(readStatusId)) {
      throw new NotFoundException("ReadStatus with id " + readStatusId + " not found");
    }
    readStatusRepository.deleteById(readStatusId);
  }
}
