package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.PrivateChannelUpdateException;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  //
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelMapper channelMapper;

  @Transactional
  @Override
  public ChannelDto create(PublicChannelCreateRequest request) {
    long start = System.currentTimeMillis();
    log.info("Create PUBLIC channel requested: name='{}'", request.name());

    Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
    channelRepository.save(channel);

    if (log.isDebugEnabled()) {
      log.debug("Created channel entity: id={}, type={}, descLen={}",
          channel.getId(), channel.getType(),
          channel.getDescription() == null ? 0 : channel.getDescription().length());
    }
    log.info("Create PUBLIC channel success: id={}, tookMs={}",
        channel.getId(), System.currentTimeMillis() - start);

    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public ChannelDto create(PrivateChannelCreateRequest request) {
    long start = System.currentTimeMillis();
    int count = request.participantIds() == null ? 0 : request.participantIds().size();
    log.info("Create PRIVATE channel requested: participants={}", count);

    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    channelRepository.save(channel);

    var users = userRepository.findAllById(request.participantIds());
    if (users.size() != count) {
      log.warn("Some participants not found: requested={}, found={}", count, users.size());
    }

    List<ReadStatus> readStatuses = users.stream()
        .map(u -> new ReadStatus(u, channel, channel.getCreatedAt()))
        .toList();
    readStatusRepository.saveAll(readStatuses);

    if (log.isDebugEnabled()) {
      var sample = users.stream().limit(5).map(u -> u.getId()).toList();
      log.debug("Private channel init read-status: sampleUserIds={}, total={}", sample, users.size());
    }

    log.info("Create PRIVATE channel success: id={}, tookMs={}",
        channel.getId(), System.currentTimeMillis() - start);
    return channelMapper.toDto(channel);
  }

  @Transactional(readOnly = true)
  @Override
  public ChannelDto find(UUID channelId) {
    long start = System.currentTimeMillis();
    log.info("Find channel requested: id={}", channelId);

    return channelRepository.findById(channelId)
        .map(entity -> {
          if (log.isDebugEnabled()) {
            log.debug("Find hit: id={}, type={}, createdAt={}",
                entity.getId(), entity.getType(), entity.getCreatedAt());
          }
          log.info("Find channel success: id={}, tookMs={}", channelId, System.currentTimeMillis() - start);
          return channelMapper.toDto(entity);
        })
        .orElseThrow(() -> {
          log.warn("Find channel not found: id={}, tookMs={}", channelId, System.currentTimeMillis() - start);
          return new ChannelNotFoundException(channelId);
        });
  }


  @Transactional(readOnly = true)
  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    long start = System.currentTimeMillis();
    log.info("Find channels by user requested: userId={}", userId);

    var subscribed = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannel)
        .map(Channel::getId)
        .toList();

    var entities = channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, subscribed);

    if (log.isDebugEnabled()) {
      var sample = entities.stream().limit(5).map(Channel::getId).toList();
      log.debug("FindByUser result sample: count={}, sampleChannelIds={}", entities.size(), sample);
    }

    log.info("Find channels by user success: userId={}, returned={}, tookMs={}",
        userId, entities.size(), System.currentTimeMillis() - start);

    return entities.stream().map(channelMapper::toDto).toList();
  }

  @Transactional
  @Override
  public ChannelDto update(UUID channelId, PublicChannelUpdateRequest request) {
    long start = System.currentTimeMillis();
    log.info("Update channel requested: id={}, newName='{}'", channelId, request.newName());

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() -> {
          log.warn("Update channel not found: id={}", channelId);
          throw new ChannelNotFoundException(channelId);
        });

    if (channel.getType() == ChannelType.PRIVATE) {
      log.warn("Update denied for PRIVATE channel: id={}", channelId);
      throw new PrivateChannelUpdateException(channelId);
    }

    if (log.isDebugEnabled()) {
      log.debug("Before update: id={}, name='{}', descLen={}",
          channel.getId(), channel.getName(),
          channel.getDescription() == null ? 0 : channel.getDescription().length());
    }

    channel.update(request.newName(), request.newDescription());

    if (log.isDebugEnabled()) {
      log.debug("After update: id={}, name='{}', descLen={}",
          channel.getId(), channel.getName(),
          channel.getDescription() == null ? 0 : channel.getDescription().length());
    }

    log.info("Update channel success: id={}, tookMs={}",
        channelId, System.currentTimeMillis() - start);
    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public void delete(UUID channelId) {
    long start = System.currentTimeMillis();
    log.info("Delete channel requested: id={}", channelId);

    if (!channelRepository.existsById(channelId)) {
      log.warn("Delete skipped (not found): id={}", channelId);
      throw new ChannelNotFoundException(channelId);
    }

    try {
      int deletedMessages = messageRepository.deleteAllByChannelId(channelId);
      int deletedRead = readStatusRepository.deleteAllByChannelId(channelId);
      channelRepository.deleteById(channelId);

      log.info("Delete channel success: id={}, deletedMessages={}, deletedReads={}, tookMs={}",
          channelId, deletedMessages, deletedRead, System.currentTimeMillis() - start);
    } catch (Exception e) {
      log.error("Delete channel failed: id={}, reason={}", channelId, e.toString(), e);
      throw new ChannelException(ErrorCode.CHANNEL_ERROR);
    }
  }
}