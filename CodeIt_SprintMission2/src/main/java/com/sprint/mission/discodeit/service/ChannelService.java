package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

    Channel create(Channel channel);
    Channel read(UUID uuid);
    List<Channel> readAll();
    Channel update(UUID id,String channelName, String channelDescription);
    boolean delete(UUID id);



}
