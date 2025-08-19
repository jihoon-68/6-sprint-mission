package com.sprint.mission.discodeit.serviece.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.serviece.ChannelServiece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelServiece implements ChannelServiece {
  private final List<Channel> channels;

  public JCFChannelServiece() {
    this.channels = new ArrayList<>();
  }

  @Override
  public void createChannel(Channel channel) {
    channels.add(channel);
  }

  @Override
  public Channel readChannel(UUID channelId) {
    return channels.stream()
        .filter(c -> c.getChannelId().equals(channelId))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void updateChannel(Channel channel) {
    for(Channel c : channels) {
      if(c.getChannelId().equals(channel.getChannelId())) {
        c.updateChannelName(channel.getChannelName());
        return;
      }
    }
  }

  @Override
  public void deleteChannel(UUID channelId) {
    channels.removeIf(c -> c.getChannelId().equals(channelId));
  }

  @Override
  public List<Channel> readAllInfo() {
    return new ArrayList<>(channels);
  }
}
