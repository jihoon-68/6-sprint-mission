package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
      Channel createChannel(String name, User root);
      Channel findChannelById(UUID id);
      List<Channel> findAllChannels();
      void updateChannel(Channel channel);
      void deleteChannel(UUID id);

      void addUserToChannel(Channel channel, User user);
      void removeUserFromChannel(Channel channel, User user);
      void addMessageToChannel(Channel channel, Message message);
      void removeMessageFromChannel(Channel channel, Message message);
}
