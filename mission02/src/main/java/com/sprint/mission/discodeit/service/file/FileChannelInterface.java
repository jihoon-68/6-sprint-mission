package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileChannelInterface extends SaveAndLoadCommon implements ChannelService, Serializable{
    private final Path path = Paths.get(System.getProperty("user.dir"), "channels");

    public FileChannelInterface() {
        init(path);
    }

    @Override
    public void addChannel(Channel channel) {
        List<Channel> channels = load(path);
        if (channels.stream().anyMatch(c -> c.getChannelName().equals(channel.getChannelName()))) {
            throw new DuplicateException("채널 이름이 이미 존재합니다.");
        }
        Path filePath = path.resolve(channel.getUuid().toString().concat(".ser"));
        save(filePath, channel);
    }

    @Override
    public void removeChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("사용자 정보를 입력해주십시오");
        }
        try{
            if(Files.exists(path)){
                Files.delete(path.resolve(channel.getUuid().toString().concat(".ser")));
                System.out.println("사용자 삭제 완료: " + channel.getUuid().toString());
            } else {
                throw new NotFoundException("사용자가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
    }

    @Override
    public List<Channel> findAllChannels() {
        List<Channel> channels = load(path);
        if (channels == null) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        return new ArrayList<>(channels);
    }

    @Override
    public Channel findChannelById(UUID id) {
        List<Channel> channels = load(path);
        if (channels == null || channels.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        return channels.stream()
                .filter(channel -> channel.getUuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
    }

    @Override
    public Channel findChannelByName(String channelName) {
        List<Channel> channels = load(path);
        if (channels == null || channels.isEmpty()) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        for(Channel channel : channels) {
            if (channel.getChannelName().equals(channelName)) {
                return channel;
            }
        }
        throw new NotFoundException("채널이 존재하지 않습니다: " + channelName);
    }

    @Override
    public void updateChannel(String channelName, String newChannelName) {
        if (channelName == null || channelName.isEmpty()) {
            throw new IllegalArgumentException("채널 이름을 입력해주십시오");
        }
        List<Channel> channels = load(path);
        if(channels.stream().noneMatch(u -> u.getChannelName().equals(channelName))) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        Channel channel = channels.stream()
                .filter(u -> u.getChannelName().equals(channelName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
        if(newChannelName == null || newChannelName.isEmpty()) {
            throw new IllegalArgumentException("새로운 채널 이름을 입력해주십시오");
        }
        if(channels.stream().anyMatch(u -> u.getChannelName().equals(newChannelName)))
            throw new NotFoundException("사용자 이름이 이미 존재합니다.");
        channels.stream()
                .filter(u -> u.getChannelName().equals(channelName))
                .findFirst()
                .ifPresent(u -> u.setChannelName(newChannelName));

        channels.forEach(this::addChannel);
        System.out.println("사용자 이름 변경 완료: " + channelName + " -> " + newChannelName);
        System.out.println("채널 이름 업데이트 완료: " + newChannelName);
    }

    @Override
    public void updateChannelDescription(String channelName, String newChannelDescription) {
        if (channelName == null || channelName.isEmpty()) {
            throw new IllegalArgumentException("채널 이름을 입력해주십시오");
        }
        List<Channel> channels = load(path);
        if(channels.stream().noneMatch(u -> u.getChannelName().equals(channelName))) {
            throw new NotFoundException("채널이 존재하지 않습니다.");
        }
        Channel channel = channels.stream()
                .filter(u -> u.getChannelName().equals(channelName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("채널이 존재하지 않습니다."));
        if(newChannelDescription == null || newChannelDescription.isEmpty()) {
            throw new IllegalArgumentException("새로운 채널 설명을 입력해주십시오");
        }
        channel.setChannelDescription(newChannelDescription);
        save(path.resolve(channel.getUuid().toString().concat(".ser")), channel);
        System.out.println("채널 설명 업데이트 완료: " + channelName + " -> " + newChannelDescription);
    }


}
