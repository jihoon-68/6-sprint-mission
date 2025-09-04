package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.stereotype.Service;

@Service
public class BasicMessageService {
    private final MessageRepository messageRepository;
    public final  ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
    }

    public Message create(Channel channel, User user, String content){
        Message message = new Message(user,content);
        messageRepository.createMessage(message);
        channelRepository.addMessageToChannel(channel, message);
        return message;
    }
}
