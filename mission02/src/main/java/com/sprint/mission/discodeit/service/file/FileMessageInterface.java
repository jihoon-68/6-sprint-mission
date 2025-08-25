package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.file.SaveAndLoadCommon;
import com.sprint.mission.discodeit.service.MessageInterface;
import com.sprint.mission.discodeit.service.UserInterface;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageInterface implements MessageInterface {
    private final MessageRepository messageRepository = new FileMessageRepository();


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
        } else {
            return messages;
        }
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        if(messages.isEmpty()) {
            throw new NotFoundException("메시지가 존재하지 않습니다.");
        } else {
            return messages;
        }
    }
}

