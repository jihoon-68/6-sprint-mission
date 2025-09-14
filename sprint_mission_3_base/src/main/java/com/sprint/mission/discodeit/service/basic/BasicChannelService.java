package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Channel.ChannelResponse;
import com.sprint.mission.discodeit.DTO.Channel.CreateChannelRequest;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;


    @Override
    public Channel createPublicChannel(CreateChannelRequest request) {
        Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
        return channelRepository.save(channel);
    }

    @Override
    public Channel createPrivateChannel(CreateChannelRequest request) {
        Channel channel = new Channel(ChannelType.PRIVATE, request.name(), request.description());
        return channelRepository.save(channel);
    }

    @Override
    public ChannelResponse find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));

        List<ReadStatus> readStatuses = readStatusRepository.findByChannelId(channel.getId())
                .orElseThrow(() -> new NoSuchElementException("ReadStatus not found with channel id " + channelId));

        Instant latest = readStatuses.stream()
                .map(x->x.getUpdatedAt())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Empty ReadStatus list"));

        List<UUID> userIds = new ArrayList<>();
        for(int i = 0; i < readStatuses.size(); i++){
            if(latest.isBefore(readStatuses.get(i).getUpdatedAt())){
                latest = readStatuses.get(i).getUpdatedAt();
            }

            userIds.add(readStatuses.get(i).getUserId());
        }

        if(channel.getType() == ChannelType.PUBLIC) {
            return new ChannelResponse(latest, null, channel);
        } else  {
            return new ChannelResponse(latest, userIds, null);
        }
    }

    @Override
    public List<ChannelResponse> findAllByUserId(UUID userId) {
        List<Channel> channels = channelRepository.findAll();
        List<ChannelResponse> channelResponses = new ArrayList<>();

        for(Channel channel : channels) {
            if(channel.getType() == ChannelType.PUBLIC) {
                channelResponses.add(find(channel.getId()));
            } else {
                ChannelResponse temp = find(channel.getId());
                if(temp != null && temp.userIds() != null && temp.userIds().contains(userId)) {
                    channelResponses.add(temp);
                }
            }
        }

        return channelResponses;
    }

    @Override
    public Channel update(UpdateChannelRequest request) {
        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + request.channelId() + " not found"));

        if(channel.getType() == ChannelType.PUBLIC) {
            channel.update(request.name(), request.description());
        } else {
            throw new IllegalArgumentException("Cannot update private channel");
        }

        return channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }
        channelRepository.deleteById(channelId);
        messageRepository.deleteByChannelId(channelId);
        readStatusRepository.deleteByChannelId(channelId);
    }
}
