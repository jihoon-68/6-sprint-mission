package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.Exception.DuplicateException;
import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.jcf.JCFChannelInterface;

import java.util.List;
import java.util.UUID;

public interface ChannelService {

    void addChannel(Channel channel) throws DuplicateException;

    void removeChannel(Channel channel) throws NotFoundException;

    List<Channel> findAllChannels() throws NotFoundException;

    Channel findChannelById(UUID id) throws NotFoundException;

    Channel findChannelByName(String channelName) throws NotFoundException;

    void updateChannel(String channelName, String newChannelName) throws NotFoundException, DuplicateException;

    void updateChannelDescription(String channel, String newChannelDescription) throws NotFoundException;

}
