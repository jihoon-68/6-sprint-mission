package com.sprint.mission.discodeit.serviece;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelServiece {
  void addChannel(Channel channel);
  Channel readChannel(UUID channelId);
  void updateChannel(Channel channel);
  void deleteChannel(UUID channelId);
  List<Channel> readAllChannel();
}
