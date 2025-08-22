package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;

public class BasicMessageService {
    private static MessageRepository messageRepository;
    public static ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
    }

    public Message create(Channel channel, User user, String content){
        Message message = messageRepository.createMessage(user,content);
        messageRepository.createMessage(user,content);
        return message;
    }
}
