package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.Channel.ChannelInfoDto;
import com.sprint.mission.discodeit.dto.Channel.CreateChannelDto;
import com.sprint.mission.discodeit.dto.Channel.PublicChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public Channel createPublicChannel(CreateChannelDto createChannelDto) {
        Channel channel = new Channel(ChannelType.PUBLIC, createChannelDto.name(), createChannelDto.description());
        return channelRepository.save(channel);
    }

    @Override
    public Channel createPrivateChannel(CreateChannelDto createChannelDto) {
        Channel channel = new Channel(ChannelType.PRIVATE, createChannelDto.name(), createChannelDto.description());

        for(User user : createChannelDto.users()) {
            ReadStatus status = new ReadStatus(user.getId(),channel.getId());
            readStatusRepository.save(status);
        }

        return channelRepository.save(channel);
    }

    @Override
    public ChannelInfoDto find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                        .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));

        List<ReadStatus> readStatus = readStatusRepository
                .findByChannelId(channel.getId())
                .orElseThrow(() -> new NoSuchElementException("ReadStatus with id " + channelId + " not found"));

        Instant latest = readStatus.stream()
                .map(x -> x.getUpdatedAt())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("readStatus is empty"));

        List<UUID> userIds = new ArrayList<>();
        for(int i = 0; i < readStatus.size(); i++){
            if(latest.isBefore(readStatus.get(i).getUpdatedAt())){
                latest = readStatus.get(i).getUpdatedAt();
            }

            userIds.add(readStatus.get(i).getUserId());
        }

        if(channel.getType().equals(ChannelType.PUBLIC)){
            return new ChannelInfoDto(latest,null,channel);
        }
        else
        {
            return new ChannelInfoDto(latest,userIds,null);
        }
    }

    @Override
    public List<ChannelInfoDto> findAllByUserId(UUID userId) {
        List<Channel> channels = channelRepository.findAll();
        List<ChannelInfoDto> channelInfos = new ArrayList<>();
        for(Channel channel : channels){
            if(channel.getType().equals(ChannelType.PUBLIC)){
                channelInfos.add(find(channel.getId()));
            }
            else
            {
                ChannelInfoDto temp = find(channel.getId());
                if(temp != null && temp.userIds() != null && temp.userIds().contains(userId)){
                    channelInfos.add(temp);
                }
            }
        }

        return channelInfos;
    }

    @Override
    public Channel update(PublicChannelUpdateDto channelUpdateDto) {
        Channel channel = channelRepository.findById(channelUpdateDto.channelID())
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelUpdateDto.channelID() + " not found"));

        if(channel.getType().equals(ChannelType.PRIVATE)){
            throw new UnsupportedOperationException("Private channels are not supported");
        }

        channel.update(channelUpdateDto.name(), channelUpdateDto.description());
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
