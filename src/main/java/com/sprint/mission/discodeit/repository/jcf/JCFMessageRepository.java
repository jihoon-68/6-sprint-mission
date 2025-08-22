package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    Map<UUID, Message> messages = new HashMap<>();
    private ChannelRepository channelRepository;
    private UserRepository userRepository;

    public JCFMessageRepository(JCFUserRepository jcfUserRepository, JCFChannelRepository jcfChannelRepository) {
        this.channelRepository = jcfChannelRepository;
        this.userRepository = jcfUserRepository;
    }

    @Override
    public void addMessage(Message message, UUID userId, UUID channelId) {
        User user = userRepository.readUser(userId);
        Channel channel = channelRepository.readChannel(channelId);
        if(user == null || channel == null) {throw new IllegalArgumentException("Missing user or channel");}

        message.setUser(user);
        message.setChannel(channel);

        messages.put(message.getMessageId(),message);
    }

    @Override
    public Message readMessage(UUID messageId) {
        return messages.get(messageId);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messages.remove(messageId);
    }

    @Override
    public void updateMessage(Message message) {
        if(messages.containsKey(message.getMessageId())) {
            messages.put(message.getMessageId(),message);
        }else{
            throw new IllegalArgumentException("Message not found");
        }
    }

    @Override
    public List<Message> readAllMessage() {
        return new ArrayList<Message>(messages.values());
    }


}
