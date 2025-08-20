package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageInterface implements MessageService {
    private final List<Message> messages;

    public JCFMessageInterface() {messages = new ArrayList<>();}

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public void removeMessage(Message message) throws NotFoundException {
        if(!messages.remove(message)) {
            throw new NotFoundException("메시지가 존재하지 않습니다.");
        }
        messages.remove(message);
    }

    @Override
    public List<Message> findAllMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public Message findMessageById(UUID id) throws NotFoundException {
        return messages.stream()
                .filter(message -> message.getUuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
    }

    @Override
    public List<Message> findMessagesBySender(UUID senderId) throws NotFoundException {
        if(senderId == null) {
            throw new IllegalArgumentException("보낸사람을 입력해주십시오");
        }
        List<Message> result = messages.stream().filter(message -> message.getSender().equals(senderId)).toList();
        if(result.isEmpty()) {
            throw new NotFoundException("보낸 메세지가 없습니다.");
        }
        return result;
    }

    @Override
    public List<Message> findMessagesByReceiver(UUID receiverId) throws  NotFoundException {
        if(receiverId == null) {
            throw new IllegalArgumentException("받는사람을 입력해주십시오");
        }
        List<Message> result = messages.stream().filter(message -> message.getReceiver().equals(receiverId)).toList();
        if(result.isEmpty()) {
            throw new NotFoundException("받은 메세지가 없습니다.");
        }
        return result;
    }

    @Override
    public void updateMessage(UUID id, String newMessageContext) throws NotFoundException {
        Message message = messages.stream()
                .filter(msg -> msg.getUuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
        message.setMessageContext(newMessageContext);
    }

}
