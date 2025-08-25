package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelInterface {

    void create(String channelName, String description) throws DuplicateException, IllegalArgumentException;

    void rename(UUID channelId, String newChannelName);

    void changeDescription(UUID channelId, String newDescription);

    void delete(UUID channelId);

    Channel getChannelById(UUID channelId);

    Channel getChannelByName(String channelName);

    List<Channel> listAllChannels();
}
