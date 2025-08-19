package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;
import java.util.stream.Collectors;

class MessageDio extends Message
{
    private User toUser;
    private User ownerUser;
    private Channel channel;

    public MessageDio(String message) {
        super(message);

        toUser = null;
        ownerUser = null;
        channel = null;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

public class JCFMessageService implements MessageService {

    private final Map<UUID,MessageDio> messages;

    public JCFMessageService() {
        messages = new HashMap<UUID,MessageDio>();
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

        AddMessage(to,owner,null,message);
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

        AddMessage(null,owner,channel,message);
        return true;
    }

    private void AddMessage(User to, User owner, Channel channel, String message)
    {
        MessageDio dio = new MessageDio(message);
        dio.setToUser(to);
        dio.setOwnerUser(owner);
        dio.setChannel(channel);

        messages.put(dio.getId(),dio);
    }

    @Override
    public List<Message> getAllMessages() {
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

        return messages.entrySet()
                .stream()
                .filter(x->x.getValue().getOwnerUser().getId().equals(owner.getId()))
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

        return messages.entrySet()
                .stream()
                .filter(x->x.getValue().getOwnerUser().getId().equals(owner.getId()))
                .filter(x->x.getValue().getChannel().getId().equals(channel.getId()))
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

        Optional<MessageDio> temp = messages.entrySet()
                .stream()
                .filter(x -> x.getValue().getOwnerUser().getId().equals(owner.getId()))
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

        Optional<MessageDio> temp = messages.entrySet()
                .stream()
                .filter(x -> x.getValue().getOwnerUser().getId().equals(owner.getId()))
                .filter(x -> x.getValue().getChannel().getId().equals(channel.getId()))
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

        if(messages.containsKey(message.getId()) == false)
        {
            System.out.println("message does not exsist. updateMessage");
            return false;
        }

        MessageDio temp = messages.get(message.getId());
        temp.updateUpdatedAt();
        temp.updateMessage(message.getMessage());
        messages.put(message.getId(),temp);
        return false;
    }

    @Override
    public boolean deleteMessage(Message message) {
        if(message == null)
        {
            System.out.println("message is null. deleteMessage");
            return false;
        }

        if(messages.containsKey(message.getId()) == false)
        {
            System.out.println("message does not exsist. deleteMessage");
            return false;
        }

        messages.remove(message.getId());
        return true;
    }
}
