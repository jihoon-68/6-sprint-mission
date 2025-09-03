package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface ChannelService {
    Channel createChannel(String name, User user);
    void renameChannel(User user, Channel ch, String newName);
    void deleteChannel(User user, Channel ch);
    void validateCreator(User user, Channel ch);
    void validateParticipant(User user, Channel channel);
}
