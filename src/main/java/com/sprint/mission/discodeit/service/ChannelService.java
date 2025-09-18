package com.sprint.mission.discodeit.service;

<<<<<<< HEAD
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
=======
import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
>>>>>>> ff6aee37135da2c11de96095adcd9502ced596ab

import java.util.List;
import java.util.UUID;

public interface ChannelService {
<<<<<<< HEAD
      Channel createChannel(String name, User root);
      Channel findChannelById(UUID id);
      List<Channel> findAllChannels();
      void updateChannel(Channel channel);
      void deleteChannel(UUID id);

      void addUserToChannel(Channel channel, User user);
      void removeUserFromChannel(Channel channel, User user);
      void addMessageToChannel(Channel channel, Message message);
      void removeMessageFromChannel(Channel channel, Message message);
=======
    Channel createPublic(CreatePublicChannelDTO createPublicChannelDTO);
    Channel createPrivate(CreatePrivateChannelDTO createPrivateChannelDTO);
    FindChannelDTO find(UUID id);
    List<FindChannelDTO> findAllByUserId(UUID userId);
    void update(UpdateChannelDTO updateChannelDTO);
    void delete(UUID id);
>>>>>>> ff6aee37135da2c11de96095adcd9502ced596ab
}
