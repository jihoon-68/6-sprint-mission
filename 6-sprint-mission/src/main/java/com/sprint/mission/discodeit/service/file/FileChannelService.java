package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileChannelService extends JCFChannelService implements ChannelService{

    Path directory = Paths.get(System.getProperty("user.dir"), "ChannelData");

    public List<Channel> channelData() {
        return FileInitSaveLoad.load(directory)
                .stream()
                .map(obj -> (Channel) obj) // Channel로 형 변환
                .collect(Collectors.toList()); // List로 다시 수집
    }

    @Override
    public Channel create(String name) {
        FileInitSaveLoad.init(directory);

        Channel channel = new Channel(name);

        Path filePath = directory.resolve(channel.getName().concat(".ser"));
        FileInitSaveLoad.save(filePath, channel);

        return channel;
    }

    @Override
    public Channel read(String name) {
        return channelData().stream().filter(ch -> ch.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public List<Channel> allRead() {
        return channelData();
    }

    @Override
    public Channel modify(UUID id) {
        return channelData().stream().filter(ch -> ch.getCommon().getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public Channel delete(UUID id) {
        return channelData().stream().filter(ch -> ch.getCommon().getId().equals(id)).findAny().orElse(null);
    }

}
