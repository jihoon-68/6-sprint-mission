package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessage implements MessageService {

    private final List<Message> messageData = new ArrayList<>();

    public void createMessage(User sender, String message) {
        Message newmessage = new Message( sender, message);
        messageData.add(newmessage);
    };

    public Message findMessageById(UUID id){
        Message target = null;
        for(Message m : messageData){
            if(m.getMessageId().equals(id)){
                target = m;
            }
        }
        return  target;
    };

    public List<Message> findAllMessages(){
        return messageData;
    };

    public Message updateMessage(Message message){
        int idx = messageData.indexOf(message);
        if(idx == -1){
            System.out.println("메세지를 찾지 못했습니다");
            return null;
        }
        messageData.add(message);
        return message;
    };

    public void deleteMessage(UUID id){
        Message message = this.findMessageById(id);
        if(message == null){
            System.out.println("메세지를 삭제 못 했습니다.");
            return;
        }
        messageData.remove(findMessageById(id));
        System.out.println("메세지를 삭제 했습니다");
    }

}
