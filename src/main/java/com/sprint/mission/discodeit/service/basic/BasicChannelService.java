package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channeldto.ChannelResponse;
import com.sprint.mission.discodeit.dto.channeldto.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.stream.Stream;
import lombok.Locked.Read;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;

  @Override
  public Channel createPublic(CreatePublicChannelRequest createPublicChannelRequest) {
    Channel channel = Channel.createPublic(createPublicChannelRequest.name(),
        createPublicChannelRequest.description());
    return channelRepository.save(channel);
  }

  // todo channel 넘기는것과 createdChannel 넘기는것 차이?
  // channel로 넘기면 인식 안됨
  public Channel createPrivate(CreatePrivateChannelRequest createPrivateChannelRequest) {
    Channel channel = Channel.createPrivate();
    Channel createdChannel = channelRepository.save(channel);
    for (UUID userId : createPrivateChannelRequest.participantIds()) {
      readStatusRepository.save(new ReadStatus(userId, createdChannel.getId(), Instant.MIN));
    }
    return createdChannel;
  }

  @Override
  public Channel find(UUID channelId) {
    return channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NoSuchElementException("Channel with id " + channelId + " not found"));
  }

  // todo 좀 더 간단하게 쓰는 방법?
  @Override
  public List<ChannelResponse> findAllByUserId(UUID userId) {

    // 유저가 속한 채널 ID 리스트
    List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannelId)
        .toList();

    // 유저가 속한 채널 리스트
    List<Channel> channelInUser = channelRepository.findAll().stream().filter(channel ->
            channel.getType().equals(ChannelType.PUBLIC) || mySubscribedChannelIds.contains(
                channel.getId()))
        .toList();

    return channelInUser.stream().map(channel -> {
      Instant lastReadAt = readStatusRepository.findAllByUserId(userId).stream()
          .filter(rs -> rs.getChannelId().equals(channel.getId()))
          .map(ReadStatus::getLastReadAt)
          .findFirst()
          .orElse(null);

      // 해당 채널의 모든 participantIds
      List<UUID> participantIds = readStatusRepository.findAllByChannelId(channel.getId()).stream()
          .map(ReadStatus::getUserId)
          .toList();

      return new ChannelResponse(channel.getId(), channel.getType(), channel.getName(),
          channel.getDescription(), participantIds, lastReadAt);

    }).toList();
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
