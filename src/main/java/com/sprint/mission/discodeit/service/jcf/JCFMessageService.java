package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data = new HashMap<>();

    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public void createMessage(String msg, UUID userId, UUID channelId) {
        if(userService.getUser(userId) != null && channelService.getChannel(channelId) != null) {
            Message message = new Message(msg, userId, channelId);
            data.put(message.getId(), message);
        } else {
            System.out.println("[Error] 유저 또는 채널이 존재하지 않습니다");
        }
    }

    @Override
    public Message getMessage(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateMessage(UUID id, String msg) {
        Message message = data.get(id);
        if (message != null) {
            message.update(msg);
        }
    }

    @Override
    public void deleteMessage(UUID id) {
        data.remove(id);
    }
}
