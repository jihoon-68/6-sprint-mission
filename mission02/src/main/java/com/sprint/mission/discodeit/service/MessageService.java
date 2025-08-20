package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.jcf.JCFMessageInterface;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    void addMessage(Message message);

    void removeMessage(Message message) throws NotFoundException;

    List<Message> findAllMessages() throws NotFoundException;

    Message findMessageById(UUID id) throws NotFoundException;

    List<Message> findMessagesBySender(UUID senderId) throws NotFoundException;

    List<Message> findMessagesByReceiver(UUID receiverId) throws NotFoundException;

    void updateMessage(UUID id, String newMessageContext) throws NotFoundException;
}
