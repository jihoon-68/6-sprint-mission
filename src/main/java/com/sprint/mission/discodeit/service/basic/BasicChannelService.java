package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;   //

  @Override
  public Channel createPublic(CreatePublicChannelRequest createPublicChannelRequest) {
    Channel channel = Channel.createPublic(createPublicChannelRequest.name(),
        createPublicChannelRequest.description());
    return channelRepository.save(channel);
  }

  public Channel createPrivate(CreatePrivateChannelRequest createPrivateChannelRequest) {
    Channel channel = Channel.createPrivate();
    Channel createdChannel = channelRepository.save(channel);
    for (UUID userId : createPrivateChannelRequest.participantIds()) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new NotFoundException("유저가 없습니다: " + userId));
      readStatusRepository.save(
          new ReadStatus(user, createdChannel, channel.getCreatedAt()));
    }
    return createdChannel;
  }

  @Override
  @Transactional(readOnly = true)
  public Channel find(UUID channelId) {
    return channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NotFoundException("Channel with id " + channelId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChannelResponse> findAllByUserId(UUID userId) {

    // 유저가 속한 채널 ID 리스트
    List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId).stream()
        .map(readStatus -> readStatus.getChannel().getId())
        .toList();

    // 유저가 속한 채널 리스트
    List<Channel> channelInUser = channelRepository.findAll().stream().filter(channel ->
            channel.getType().equals(ChannelType.PUBLIC) || mySubscribedChannelIds.contains(
                channel.getId()))
        .toList();

    return channelInUser.stream().map(channel -> {
      // 생성 시간 역순에서 첫번째가 마지막 메시지 시간
      Instant lastMessageAt = messageRepository.findAllByChannelId(channel.getId()).stream()
          .map(Message::getCreatedAt)
          .sorted(Comparator.reverseOrder())
          .limit(1)
          .findFirst().orElse(Instant.MIN);

      // 해당 private 채널의 모든 participantIds
      List<UUID> participantIds = new ArrayList<>();
      if (channel.getType().equals(ChannelType.PRIVATE)) {
        participantIds = readStatusRepository.findAllByChannelId(channel.getId()).stream()
            .map(readStatus -> readStatus.getUser().getId()).distinct()
            .collect(Collectors.toList());
      }

      return new ChannelResponse(channel.getId(), channel.getType(), channel.getName(),
          channel.getDescription(), participantIds, lastMessageAt);

    }).toList();
  }

  @Override
  public Channel update(UUID channelId, UpdateChannelRequest updateChannelRequest) {
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(
            () -> new NotFoundException("Channel with id " + channelId + " not found"));

    channel.update(updateChannelRequest.newName(), updateChannelRequest.newDescription());
    return channelRepository.save(channel);
  }

  @Override
  public void delete(UUID channelId) {
    if (!channelRepository.existsById(channelId)) {
      throw new NotFoundException("Channel with id " + channelId + " not found");
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
