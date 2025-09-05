package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    public final  ChannelRepository channelRepository;
    public final UserRepository userRepository;

    @Override
    public Message create(UUID channel, UUID user, String content){
        boolean sender = userRepository.existsById(user);
        boolean ReceiverChannel= channelRepository.existsById(channel);
        if(!sender || !ReceiverChannel){
            throw new NullPointerException("Sender Or Receiver channels are mandatory");
        }
        return messageRepository.save(new Message(channel,user,content));
    }

    @Override
    public Message find(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new  NullPointerException("Message not found"));
    }

    @Override
    public List<Message> findAll() {
        return List.copyOf(messageRepository.findAll());
    }

    @Override
    public void update(Message message) {
        Message messageUpdated = find(message.getId());
        messageUpdated.updateMessage(message.getContent());
        messageRepository.save(messageUpdated);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }
}
