package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    void createChannel(Channel channel);
    Channel findChannelById(UUID id);
    List<Channel> findAllChannels();
    void updateChannel(Channel channel);
    void deleteChannel(UUID id);

    //체널에 메시지 추가
    void addMessageToChannel(Channel channel, Message message);
    void removeMessageFromChannel(Channel channel, Message message);

    /*
    //구현 순위 낮음
    //체널에 유저 추가
    void addUserToChannel(Channel channel, User user);
    void removeUserFromChannel(Channel channel, User user);
    */
}
