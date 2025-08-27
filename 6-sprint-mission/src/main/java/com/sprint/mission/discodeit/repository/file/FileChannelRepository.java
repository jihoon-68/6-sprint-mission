package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileChannelRepository implements ChannelRepository {

    Path directory = Paths.get(System.getProperty("user.dir"), "ChannelData");

    @Override
    public Channel find(UUID id) {
        return findall()
                .stream()
                .filter(ch -> ch.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public Channel save(Channel channel) {
        FileInitSaveLoad.init(directory);

        Path filePath = directory.resolve(channel.getName().concat(".ser"));
        FileInitSaveLoad.save(filePath, channel);

        return channel;
    }

    @Override
    public List<Channel> findall() {
        return FileInitSaveLoad.<Channel>load(directory);
    }

    @Override
    public void delete(UUID id) {
        findall().removeIf(ch -> ch.getCommon().getId().equals(id));
    }

}
