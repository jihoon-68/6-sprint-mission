package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;

    public JCFMessageService(){
        this.data = new LinkedHashMap<>();
    }

    @Override
    public Message create(Message message){
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Message read(UUID id){
        if (data.containsKey(id)){
            return data.get(id);
        }
        else{
            System.out.println("해당 ID에 맞는 채널이 없습니다");
            return null;
        }
    }

    @Override
    public List<Message> readAll(){
        return new ArrayList<>(data.values());
    }

    @Override
    public Message update(UUID id, String senderName, String messageContent, String reciverName) {
        Message message = data.get(id);
        if (message != null) {
            message.setSenderName(senderName);
            message.setMessageContent(messageContent);
            message.setReciverName(reciverName);

        }
        return message;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}
