package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.Enum.ChannelType;
import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;

    @Override
    public Channel createPublic(CreatePublicChannelDTO createPublicChannelDTO) {
        return channelRepository.save(new Channel(createPublicChannelDTO));
    }

    @Override
    public Channel createPrivate(CreatePrivateChannelDTO createPrivateChannelDTO) {
        Channel channel = new Channel(createPrivateChannelDTO.channelType());

        createPrivateChannelDTO.userIds()
                .forEach(userId ->
                        readStatusRepository.save(new ReadStatus(new CreateReadStatusDTO(userId,channel.getId()))));

        return channelRepository.save(channel);
    }

    @Override
    public FindChannelDTO find(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Channel not found"));

        Message message = messageRepository.findAll().stream()
                .filter(m -> m.getChannel().equals(id))
                .findFirst().orElseThrow(() -> new NullPointerException("Channel not found"));

        if(channel.getType().equals(ChannelType.PRIVATE)){
            return new FindChannelDTO(channel,message.getCreated(),message.getSender());
        }

        return new FindChannelDTO(channel,message.getCreated(),null);
    }

    @Override
    public List<FindChannelDTO> findAllByUserId(UUID userId) {
        List<ReadStatus> readStatuses = readStatusRepository.findAll().stream()
                .filter(rs -> rs.getUserId().equals(userId))
                .toList();

        Map<Channel,List<Message>> channelMessageMap = readStatuses.stream()
                .collect(Collectors.toMap(rs-> channelRepository.findById(rs.getChannelId()).orElse(null),
                        rs -> messageRepository.findAll().stream()
                                .filter(m -> m.getChannel().equals(rs.getChannelId())).limit(1).toList())
                );



        return readStatuses.stream()
                .map(rs -> {
                    Channel channel = channelRepository.findById(rs.getChannelId())
                            .orElseThrow(() -> new NullPointerException("Channel not found"));
                    if(channelMessageMap.get(channel).isEmpty()){
                        return new FindChannelDTO(channel,channel.getCreated(),rs.getUserId());
                    }
                    return new FindChannelDTO(channel,channelMessageMap.get(channel).get(0).getCreated(),rs.getUserId());
                })
                .toList();
    }

    @Override
    public void update(UpdateChannelDTO updateChannelDTO) {
        Channel channel = channelRepository.findById(updateChannelDTO.id())
                .orElseThrow(() -> new NullPointerException("Channel not found"));
        if(channel.getType().equals(ChannelType.PRIVATE)){
            return;
        }
        channel.update(updateChannelDTO.name(), updateChannelDTO.description());
        channelRepository.save(channel);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.findAll().stream()
                .filter(rs -> rs.getChannelId().equals(id))
                .forEach(rsDelete -> readStatusRepository.deleteById(rsDelete.getId()));

        messageRepository.findAll().stream()
                .filter(m -> m.getChannel().equals(id))
                .forEach(mDelete -> readStatusRepository.deleteById(mDelete.getId()));

        channelRepository.deleteById(id);
    }
}
