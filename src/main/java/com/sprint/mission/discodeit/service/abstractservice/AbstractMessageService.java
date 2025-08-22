package com.sprint.mission.discodeit.service.abstractservice;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AbstractMessageService implements MessageService {

    protected final MessageRepository messageRepository;

    public AbstractMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public boolean CreateMessage(User to, User owner, String message) {
        if(to == null)
        {
            System.out.println("to user is null");
            return false;
        }

        if(owner == null)
        {
            System.out.println("owner user is null. DirectMsg");
            return false;
        }

        if(message.isEmpty())
        {
            System.out.println("message is empty. DirectMsg");
            return false;
        }

        AddMessage(message,owner.getId(), to.getId(), null);
        return true;
    }

    @Override
    public boolean CreateMessage(User owner, Channel channel, String message) {
        if(channel == null)
        {
            System.out.println("channel is null");
            return false;
        }

        if(owner == null)
        {
            System.out.println("owner user is null. ChannelMsg");
            return false;
        }

        if(message.isEmpty())
        {
            System.out.println("message is empty. ChannelMsg");
            return false;
        }

        AddMessage(message,owner.getId(),null, channel.getId());
        return true;
    }

    private void AddMessage(String message, UUID owner, UUID to, UUID channel)
    {
        Message msg = new Message(message, owner, to, channel);
        messageRepository.save(msg);
    }

    @Override
    public List<Message> getAllMessages() {
        Map<UUID, Message> messages = messageRepository.getMessages();
        if(messages == null)
        {
            System.out.println("messages are empty. getAllMessages");
            return null;
        }

        return messages.entrySet()
                .stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAllMessages(User owner) {
        if(owner == null)
        {
            System.out.println("owner user is null. getAllMessages(User)");
            return null;
        }

        Map<UUID, Message> messages = messageRepository.getMessages();
        if(messages == null)
        {
            System.out.println("messages are empty. getAllMessages(User)");
            return null;
        }

        return messages.entrySet()
                .stream()
                .filter(x->x.getValue().getOwnerUserId().equals(owner.getId()))
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAllMessages(User owner, Channel channel) {
        if(owner == null)
        {
            System.out.println("owner user is null. getAllMessages(User,channel)");
            return null;
        }

        if(channel == null)
        {
            System.out.println("channel is null. getAllMessages(User,channel)");
            return null;
        }

        Map<UUID, Message> messages = messageRepository.getMessages();
        if(messages == null)
        {
            System.out.println("messages are empty. getAllMessages(User,channel)");
            return null;
        }

        return messages.entrySet()
                .stream()
                .filter(x->x.getValue().getOwnerUserId().equals(owner.getId()))
                .filter(x->x.getValue().getChannelId().equals(channel.getId()))
                .map(x -> x.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public Message getMessage(User owner) {
        if(owner == null)
        {
            System.out.println("owner user is null. getMessage(User)");
            return null;
        }

        Map<UUID, Message> messages = messageRepository.getMessages();
        if(messages == null)
        {
            System.out.println("messages are empty. getMessage(User)");
            return null;
        }

        Optional<Message> temp = messages.entrySet()
                .stream()
                .filter(x -> x.getValue().getOwnerUserId().equals(owner.getId()))
                .map(x -> x.getValue())
                .findFirst();

        return temp.orElse(null);
    }

    @Override
    public Message getMessage(User owner, Channel channel) {
        if(owner == null)
        {
            System.out.println("owner user is null. getMessage(User,channel)");
            return null;
        }

        if(channel == null)
        {
            System.out.println("channel is null. getMessage(User,channel)");
            return null;
        }

        Map<UUID, Message> messages = messageRepository.getMessages();
        if(messages == null)
        {
            System.out.println("messages are empty. getMessage(User,channel)");
            return null;
        }

        Optional<Message> temp = messages.entrySet()
                .stream()
                .filter(x -> x.getValue().getOwnerUserId().equals(owner.getId()))
                .filter(x -> x.getValue().getChannelId().equals(channel.getId()))
                .map(x -> x.getValue())
                .findFirst();

        return temp.orElse(null);
    }

    @Override
    public boolean updateMessage(Message message) {
        if(message == null)
        {
            System.out.println("message is null. updateMessage");
            return false;
        }

        return messageRepository.update(message);
    }

    @Override
    public boolean deleteMessage(Message message) {
        if(message == null)
        {
            System.out.println("message is null. deleteMessage");
            return false;
        }

        return messageRepository.delete(message);
    }
}
