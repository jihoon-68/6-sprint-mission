package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {
    public Channel createChannel(String name, User root);
    public Channel findChannelById(UUID id);
    public List<Channel> findAllChannels();
    public void updateChannel(Channel channel);
    public void deleteChannel(UUID id);

    //체널에 메시지 추가
    public void addMessageToChannel(Channel channel, Message message);
    public void removeMessageFromChannel(Channel channel, Message message);

    /*
    //구현 순위 낮음
    //체널에 유저 추가
    public void addUserToChannel(Channel channel, User user);
    public void removeUserFromChannel(Channel channel, User user);
    */
}
