package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.Enum.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    @Override
    public Channel create(String channelName, String text, ChannelType type){
        return channelRepository.save(new Channel(channelName, text, type));
    }

    @Override
    public Channel find(UUID id) {
        return channelRepository.findById(id).orElseThrow(() -> new NullPointerException("Channel not found") );
    }

    @Override
    public List<Channel> findAll() {
        return List.copyOf(channelRepository.findAll());
    }

    @Override
    public void update(Channel channel) {
        Channel channelToUpdate = find(channel.getId());
        channelToUpdate.update(channel.getName(),channel.getDescription());
        channelRepository.save(channelToUpdate);
    }

    @Override
    public void delete(UUID id) {
        channelRepository.deleteById(id);
    }
}
