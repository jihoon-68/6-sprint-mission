package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

    Channel createChannel(String name, String description);

    Channel getChannelById(UUID id);

    List<Channel> getAllChannels();

    Channel updateChannel(UUID id, String newName, String newDescription);

    boolean deleteChannel(UUID id);


}
