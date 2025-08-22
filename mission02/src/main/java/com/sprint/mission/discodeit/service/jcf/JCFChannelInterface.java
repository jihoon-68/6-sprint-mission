package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelInterface implements ChannelService {
    private final List<Channel> channels;

    public JCFChannelInterface(){
        this.channels = new ArrayList<>();
    }

    @Override
    public void addChannel(Channel channel) throws DuplicateException {
        if(channels.stream().anyMatch(c -> c.getChannelName().equals(channel.getChannelName()))) {
            throw new DuplicateException("채널이름: " + channel.getChannelName() + " 이(가) 이미 존재합니다.");
        }
        channels.add(channel);
    }

    @Override
    public void removeChannel(Channel channel) throws NotFoundException {
        if (!channels.remove(channel)) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
    }

    @Override
    public List<Channel> findAllChannels() {
        return new ArrayList<>(channels);
    }

    @Override
    public Channel findChannelById(UUID id) throws NotFoundException {
        if(channels.stream().noneMatch(channel -> channel.getUuid().equals(id))){
            throw new NotFoundException("채널이 존재하지 않습니다.");
        } else {
            return channels.stream().filter(channel -> channel.getUuid().equals(id))
                    .findFirst().orElse(null);
        }
    }

    @Override
    public Channel findChannelByName(String channelName) throws NotFoundException {
        return channels.stream()
                .filter(channel -> channel.getChannelName().equals(channelName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
    }

    @Override
    public void updateChannel(String channelName, String newChannelName) throws NotFoundException, DuplicateException{
        Channel channel = channels.stream().filter(channel1 -> channel1.getChannelName().equals(channelName))
                .findFirst().orElseThrow(()-> new NotFoundException("채널이 존재하지 않습니다."));
        if (channels.stream().anyMatch(c -> c.getChannelName().equals(newChannelName))) {
            throw new DuplicateException("채널이름: " + newChannelName + " 이(가) 이미 존재합니다.");
        }
        channel.setChannelName(newChannelName);
    }

    @Override
    public void updateChannelDescription(String channelName, String newChannelDescription) throws NotFoundException {
        Channel channel = channels.stream().filter(channel1 -> channel1.getChannelName().equals(channelName))
                .findFirst().orElseThrow(()-> new NotFoundException("채널이 존재하지 않습니다."));
        channel.setChannelDescription(newChannelDescription);
    }

}
