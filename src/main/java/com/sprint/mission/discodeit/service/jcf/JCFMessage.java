package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessage implements MessageService {

    private final List<Message> messageData;

    public JCFMessage(){
        messageData = new ArrayList<>();
    }

    public Message createMessage(User sender, String message) {
        Message newmessage = new Message( sender, message);
        messageData.add(newmessage);
        return newmessage;
    };

    public Message findMessageById(UUID id){

        return  messageData.stream()
                .filter(message -> message.getId().equals(id))
                .findAny()
                .orElse(null);
    };

    public List<Message> findAllMessages(){

        if(messageData.isEmpty()){
            throw new NullPointerException("현재 메시지 없습니다.");
        }
        return messageData;
    };

    public void updateMessage(Message message){
        int idx = messageData.indexOf(message);
        if(idx == -1){
            throw new NullPointerException("해당 메시지 없습니다.");
        }
        messageData.set(idx,message);

    };

    public void deleteMessage(UUID id){
        Message message = this.findMessageById(id);
        if(message == null){
            throw new NullPointerException("삭제 오류 발생");
        }
        messageData.remove(message);
    }

}
