package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileChannelService implements ChannelService {

    Path directory = Paths.get(System.getProperty("user.dir"), "ChannelData");

    @Override
    public Channel create(String name) {
        FileInitSaveLoad.init(directory);

        Channel channel = new Channel(name);

        Path filePath = directory.resolve(channel.getName().concat(".ser"));
        FileInitSaveLoad.<Channel>save(filePath, channel);

        return channel;
    }

    @Override
    public Channel read(String name) {
        return allRead()
                .stream()
                .filter(ch -> ch.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Channel> allRead() {
        return FileInitSaveLoad.<Channel>load(directory);
    }

    @Override
    public Channel modify(UUID id) {
        return allRead()
                .stream()
                .filter(ch -> ch.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public Channel delete(UUID id) {
        return allRead()
                .stream().filter(ch -> ch.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

}
