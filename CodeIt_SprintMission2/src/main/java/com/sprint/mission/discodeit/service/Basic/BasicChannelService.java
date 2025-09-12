package com.sprint.mission.discodeit.service.Basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.UUID;

public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel createChannel(String name, String description) {
        return channelRepository.createChannel(name, description);
    }

    @Override
    public Channel getChannelById(UUID id) {
        Channel channel = channelRepository.getChannelById(id);
        if (channel == null) {
            System.out.println("해당 ID에 맞는 채널이 없습니다.");
        }
        return channel;
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.getAllChannels();
    }

    @Override
    public Channel updateChannel(UUID id, String newName, String newDescription) {
        return channelRepository.updateChannel(id, newName, newDescription);
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return channelRepository.deleteChannel(id);
    }
}
