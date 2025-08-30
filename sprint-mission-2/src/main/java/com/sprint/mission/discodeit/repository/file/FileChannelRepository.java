package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/ChannelDate");
    private static final FileEdit instance = new  FileEdit();

    private Path filePaths(Channel channel) {
        return directory.resolve(channel.getChannelId().toString() + ".ser");
    }

    public FileChannelRepository(){
        instance.init(directory);
    }

    public void createChannel(Channel channel) {
        instance.save(filePaths(channel), channel);
    }

    public Channel findChannelById(UUID id) {
        List<Channel> channelList = instance.load(directory);
        return channelList.stream()
                .filter(channel -> channel.getChannelId().equals(id))
                .findAny().orElse(null);
    }

    public List<Channel> findAllChannels() {
        return instance.load(directory);
    }

    public void updateChannel(Channel channel) {
        instance.save(filePaths(channel), channel);
    }

    public void deleteChannel(UUID id) {
        instance.delete(filePaths(findChannelById(id)));
    }

    public void addMessageToChannel(Channel channel, Message message) {

    }

    public void removeMessageFromChannel(Channel channel, Message message) {

    }
}
