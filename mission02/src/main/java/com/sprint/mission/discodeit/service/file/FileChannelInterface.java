package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.service.ChannelInterface;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class FileChannelInterface implements ChannelInterface{
    private final ChannelRepository channelRepository = new FileChannelRepository();

    @Override
    public void create(String channelName, String description) throws DuplicateException, IllegalArgumentException {
        if(channelRepository.existsByName(channelName)) {
            throw new DuplicateException("채널이름: " + channelName + " 이(가) 이미 존재합니다.");
        }
        Channel channel = new Channel(channelName, description);
        channelRepository.save(channel);
    }

    @Override
    public void rename(UUID channelId, String newChannelName) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
        channelRepository.findByName(newChannelName)
                .filter(c -> !c.getUuid().equals(channelId))
                .ifPresent(c -> {throw new DuplicateException("채널이름: " + newChannelName + " 이(가) 이미 존재합니다.");});
        channel.setChannelName(newChannelName);
        channelRepository.save(channel);
    }

    @Override
    public void changeDescription(UUID channelId, String newDescription) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
        channel.setChannelDescription(newDescription);
        channelRepository.save(channel);
    }

    @Override
    public void delete(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
        channelRepository.remove(channel);
    }

    @Override
    public Channel getChannelById(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
    }

    @Override
    public Channel getChannelByName(String channelName) {
        return channelRepository.findByName(channelName)
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
    }

    @Override
    public List<Channel> listAllChannels() {
        List<Channel> channels = channelRepository.findAll();
        if (channels == null || channels.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        return channels;
    }
}
