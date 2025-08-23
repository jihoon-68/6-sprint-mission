package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private final String filePath = "message.rep";
    private Map<UUID, Message> messages = new HashMap<>();
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public FileMessageRepository(UserRepository userRepository, ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    private void loadMessages() {
        try(FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            messages = (Map<UUID, Message>) ois.readObject();
        }catch(FileNotFoundException e){
            messages = new HashMap<>();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void saveMessages() {
        try(FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(messages);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addMessage(Message message, UUID userId, UUID channelId) {
        User user = userRepository.readUser(userId);
        Channel channel = channelRepository.readChannel(channelId);

        if(user == null || channel == null) {
            throw new IllegalArgumentException("user or channel is null");
        }
        message.setUser(user);
        message.setChannel(channel);
        messages.put(message.getMessageId(),message);
    }

    @Override
    public Message readMessage(UUID messageId) {
        return messages.get(messageId);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messages.remove(messageId);
    }

    @Override
    public List<Message> readAllMessage() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public void updateMessage(Message message) {
        if(messages.containsKey(message.getMessageId())) {
            messages.put(message.getMessageId(),message);
        }else{
            throw new IllegalArgumentException("message id not found");
        }
    }
}
