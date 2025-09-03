package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.sprint.mission.discodeit.entity.Channel.ChannelType;

public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel create(ChannelType channelType, String channelName, String description) {
        return channelRepository.save(Channel.of(channelType, channelName, description));
    }

    @Override
    public Optional<Channel> read(UUID id) {
        return channelRepository.find(id);
    }

    @Override
    public Set<Channel> readAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel setPublicChannel(UUID id) {
        Channel channel = channelRepository.find(id).orElseThrow();
        return channelRepository.save(channel.setPublicChannel());
    }

    @Override
    public Channel updateChannelName(UUID id, String newChannelName) {
        Channel channel = channelRepository.find(id).orElseThrow();
        return channelRepository.save(channel.updateChannelName(newChannelName));
    }

    @Override
    public Channel updateDescription(UUID id, String newDescription) {
        Channel channel = channelRepository.find(id).orElseThrow();
        return channelRepository.save(channel.updateDescription(newDescription));
    }

    @Override
    public void delete(UUID id) {
        channelRepository.delete(id);
    }
}
