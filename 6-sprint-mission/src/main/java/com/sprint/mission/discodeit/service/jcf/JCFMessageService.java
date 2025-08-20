package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final List<Message> messageData;
    private static final JCFMessageService INSTANCE = new JCFMessageService();

    private JCFMessageService() {
        this.messageData = new ArrayList<>();
    }

    public static JCFMessageService getInstance(){
        return INSTANCE;
    }

    @Override
    public Message read(String sender){
        return messageData.stream().filter(msg->msg.getSender().equals(sender)).findAny().orElse(null);
    }

    @Override
    public Message create(User sender, User reciever, String content, Channel channel){
        Message message = new Message(sender,reciever,content,channel);
        messageData.add(message);
        return message;
    }

    @Override
    public List<Message> allRead(){
        return messageData;
    }

    @Override
    public Message modify(UUID id){
        return messageData.stream().filter(msg->msg.getCommon().getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public Message delete(UUID id){
        return messageData.stream().filter(msg->msg.getCommon().getId().equals(id)).findAny().orElse(null);
    }

}
