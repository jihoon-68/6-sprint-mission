package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {
    private static List<Channel> channels;

    public JCFChannelRepository() {

        channels = new ArrayList<>();
    }

    public Channel createChannel(String name, User root) {
        Channel channel = new Channel(name, root);
        channels.add(channel);
        return channel;
    }

    public Channel findChannelById(UUID id) {

        return channels.stream()
                .filter(channel -> channel.getChannelId().equals(id))
                .toList()
                .get(0);
    }

    public List<Channel> findAllChannels() {
        return channels;
    }

    public void updateChannel(Channel channel) {
        int idx = channels.indexOf(channel);
        if (idx == -1) {
            System.out.println("해당 유저 없음");
            return;
        }
        channels.set(idx, channel);
    }

    public void deleteChannel(UUID id) {
        channels.remove(findChannelById(id));
    }

    public void addMessageToChannel(Channel channel, Message message) {
        channel.updateChanelMessages(message);
        updateChannel(channel);
    }

    public void removeMessageFromChannel(Channel channel, Message message) {

    }
}
