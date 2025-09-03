package com.sprint.mission.discodeit.serviece.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.serviece.ChannelServiece;
import com.sprint.mission.discodeit.serviece.MessageServiece;
import com.sprint.mission.discodeit.serviece.UserServiece;

import java.io.*;
import java.util.*;

public class FileMessageService implements MessageServiece {
    private final String filePath = "message.ser";
    private Map<UUID, Message> messages = new HashMap<>();
    private final UserServiece userServiece;
    private final ChannelServiece channelServiece;

    public FileMessageService(UserServiece userServiece, ChannelServiece channelServiece) {
        this.userServiece = userServiece;
        this.channelServiece = channelServiece;
        loadMessages();
    }

    @SuppressWarnings("unchecked")
    private void loadMessages(){
        try(FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ){
            messages = (Map<UUID, Message>) ois.readObject();
        }catch (FileNotFoundException e){
            messages = new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveMessages(){
        try(FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(messages);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addMessage(Message message, UUID userId, UUID channelId) {
        User user = userServiece.readUser(userId);
        Channel channel = channelServiece.readChannel(channelId);

        if(user == null || channel == null){
            throw new IllegalArgumentException("Could not find user or channel");
        }
        message.setUser(user);
        message.setChannel(channel);

        messages.put(message.getMessageId(),message);
        saveMessages();
    }

    @Override
    public Message readMessage(UUID messageId) {
        return messages.get(messageId);
    }

    @Override
    public void updateMessage(Message message) {
        messages.put(message.getMessageId(),message);
        saveMessages();
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messages.remove(messageId);
        saveMessages();
    }

    @Override
    public List<Message> readAllMessage() {
        return new ArrayList<>(messages.values());
    }
}
