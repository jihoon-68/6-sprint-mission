package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelResponse;
import com.sprint.mission.discodeit.dto.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;

    @Override
    public ChannelResponse createPublicChannel(CreatePublicChannelRequest request) {
        Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
        channelRepository.save(channel);
        return toChannelResponse(channel);
    }

    @Override
    public ChannelResponse createPrivateChannel(CreatePrivateChannelRequest request) {
        request.participantIds().forEach(userId -> {
            if (!userRepository.existsById(userId)) {
                throw new NoSuchElementException("User not found with id: " + userId);
            }
        });

        Channel channel = new Channel(ChannelType.PRIVATE, null, null);
        channelRepository.save(channel);

        request.participantIds().forEach(userId -> {
            ReadStatus readStatus = new ReadStatus(userId, channel.getId());
            readStatusRepository.save(readStatus);
        });

        return toChannelResponse(channel);
    }

    @Override
    public ChannelResponse find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
        return toChannelResponse(channel);
    }

    @Override
    public List<ChannelResponse> findAllByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User not found with id: " + userId);
        }

        List<Channel> publicChannels = channelRepository.findAll()
                .stream()
                .filter(c -> c.getType() == ChannelType.PUBLIC)
                .collect(Collectors.toList());

        List<Channel> privateChannels = readStatusRepository.findByUserId(userId)
                .stream()
                .map(rs -> channelRepository.findById(rs.getChannelId()).orElse(null))
                .filter(c -> c != null && c.getType() == ChannelType.PRIVATE)
                .collect(Collectors.toList());

        return Stream.concat(publicChannels.stream(), privateChannels.stream())
                .map(this::toChannelResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ChannelResponse update(UUID channelId, UpdateChannelRequest request) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));

        if (channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalArgumentException("Cannot update a private channel");
        }

        channel.update(request.name(), request.description());
        channelRepository.save(channel);
        return toChannelResponse(channel);
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }

        messageRepository.deleteAllByChannelId(channelId);
        readStatusRepository.deleteAllByChannelId(channelId);
        channelRepository.deleteById(channelId);
    }

    private ChannelResponse toChannelResponse(Channel channel) {
        Instant lastMessageTimestamp = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
                .map(m -> m.getCreatedAt())
                .orElse(null);

        List<UUID> participantIds = null;
        if (channel.getType() == ChannelType.PRIVATE) {
            participantIds = readStatusRepository.findByChannelId(channel.getId())
                    .stream()
                    .map(ReadStatus::getUserId)
                    .collect(Collectors.toList());
        }

        return ChannelResponse.of(channel, lastMessageTimestamp, participantIds);
    }
}
