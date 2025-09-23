package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channeldto.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;

  @Override
  public Channel createPublic(CreatePublicChannelRequest createPublicChannelRequest) {
    Channel channel = Channel.createPublic(createPublicChannelRequest.name(),
        createPublicChannelRequest.description());
    return channelRepository.save(channel);
  }

  public Channel createPrivate(CreatePrivateChannelRequest createPrivateChannelRequest) {
    Channel channel = Channel.createPrivate();
    for (UUID userId : createPrivateChannelRequest.participantIds()) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("참가자 id가 실제 유저에 있지 않음: " + userId));
      readStatusRepository.save(new ReadStatus(userId, channel.getId(), Instant.now()));
    }
    return channelRepository.save(channel);
  }

  @Override
  public Channel find(UUID channelId) {
    return channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));
  }

  @Override
  public List<Channel> findAllByUserId(UUID userId) {
    List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannelId)
        .toList();

    return channelRepository.findAll().stream()
        .filter(channel ->
            channel.getType().equals(ChannelType.PUBLIC)
                || mySubscribedChannelIds.contains(channel.getId())
        ).toList();
  }

  @Override
  public Channel update(UUID channelId, UpdateChannelRequest updateChannelRequest) {
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));

    channel.update(updateChannelRequest.newName(), updateChannelRequest.newDescription());
    return channelRepository.save(channel);
  }

  @Override
  public void delete(UUID channelId) {
    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel with id " + channelId + " not found");
    }
    // 같은 채널에서 나온 읽기상태들 삭제
    List<ReadStatus> readStatusesInChannel = readStatusRepository.findAllByChannelId(channelId);
    for (ReadStatus readStatus : readStatusesInChannel) {
      readStatusRepository.deleteById(readStatus.getId());
    }
    // 같은 채널에서 나온 메시지들 삭제
    List<Message> messagesInChannel = messageRepository.findAllByChannelId(channelId);
    for (Message message : messagesInChannel) {
      messageRepository.deleteById(message.getId());
    }
    // 채널 id로 삭제
    channelRepository.deleteById(channelId);
  }
}
