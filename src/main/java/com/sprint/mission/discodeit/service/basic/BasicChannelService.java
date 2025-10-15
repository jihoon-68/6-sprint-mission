package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import com.sprint.mission.discodeit.service.ChannelService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelMapper channelMapper;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public Channel create(PublicChannelCreateRequest request) {
    String name = request.name();
    String description = request.description();
    Channel channel = new Channel(ChannelType.PUBLIC, name, description);

    return channelRepository.save(channel);
  }

  @Override
  @Transactional
  public Channel create(PrivateChannelCreateRequest request) {

    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    Channel createdChannel = channelRepository.save(channel);

    Instant now = Instant.now();
    request.participantIds().stream()
        .map(userId -> userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found")))
        .map(user -> {
          ReadStatus readStatus = new ReadStatus(user, createdChannel, Instant.now());
          return readStatusRepository.save(readStatus);
        })
        .toList();

    return createdChannel;
  }

  @Override
  public ChannelDto find(UUID channelId) {
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new EntityNotFoundException("Channel with id " + channelId + " not found"));
    return channelMapper.toDto(channel);
  }

  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<Channel> privateChannels = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannel)
        .toList();

    List<Channel> publicChannels = channelRepository.findAll().stream()
        .filter(channel -> channel.getType().equals(ChannelType.PUBLIC))
        .toList();

    Set<Channel> combinedChannels = new HashSet<>();
    combinedChannels.addAll(privateChannels);
    combinedChannels.addAll(publicChannels);

    return combinedChannels.stream()
        .map(channelMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public Channel update(UUID channelId, PublicChannelUpdateRequest request) {
    String newName = request.newName();
    String newDescription = request.newDescription();

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new EntityNotFoundException("Channel with id " + channelId + " not found"));

    if (channel.getType().equals(ChannelType.PRIVATE)) {
      throw new IllegalArgumentException("Private channel cannot be updated");
    }

    channel.update(newName, newDescription);

    return channel;
  }


  @Override
  @Transactional
  public void delete(UUID channelId) {

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new EntityNotFoundException("Channel with id " + channelId + " not found"));

    channelRepository.delete(channel);
  }

  public Instant getLastMessageAt(Channel channel) {
    return messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
        .map(Message::getCreatedAt)
        .orElse(Instant.MIN);
  }

  public List<UserDto> getParticipants(Channel channel) {
    if (channel.getType().equals(ChannelType.PRIVATE)) {
      return readStatusRepository.findAllByChannelId(channel.getId())
          .stream()
          .map(ReadStatus::getUser)
          .map(userMapper::toDto)
          .toList();
    }
    return List.of();
  }
}