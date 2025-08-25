package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.service.MessageInterface;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageInterface implements MessageInterface {
    private final MessageRepository messageRepository = new JCFMessageRepository();

    @Override
    public void send(String messageContext, UUID senderId, UUID receiverId) {
        Message message = new Message(messageContext, senderId, receiverId);
        messageRepository.save(message);
    }

    @Override
    public void changeContext(UUID messageId, String newMessageContext) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
        message.setMessageContext(newMessageContext);
        messageRepository.save(message);
    }

    @Override
    public void delete(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
        messageRepository.remove(message);
    }

    @Override
    public String getMessageById(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
        return message.getMessageContext();
    }

    @Override
    public List<Message> getMessagesByReceiver(UUID receiverId) {
        List<Message> messages = messageRepository.findByReceiverId(receiverId);
        if(messages.isEmpty()) {
            throw new NotFoundException("받는 사람 아이디에 해당하는 메시지가 존재하지 않습니다.");
        }
        return new ArrayList<>(messages);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        if(messages.isEmpty()) {
            throw new NotFoundException("메시지가 존재하지 않습니다.");
        }
        return new ArrayList<>(messages);
    }
}
