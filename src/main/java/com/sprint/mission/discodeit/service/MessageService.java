package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface MessageService {
    public boolean CreateMessage(User to, User owner, String message);
    public boolean CreateMessage(User owner, Channel channel, String message);

    public List<Message> getAllMessages();
    public List<Message> getAllMessages(User owner);
    public List<Message> getAllMessages(User owner,  Channel channel);

    public Message getMessage(User owner);
    public Message getMessage(User owner, Channel channel);


    public boolean updateMessage(Message message);
    public boolean deleteMessage(Message message);
}
