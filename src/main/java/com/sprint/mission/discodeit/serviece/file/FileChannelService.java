package com.sprint.mission.discodeit.serviece.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.serviece.ChannelServiece;

import java.io.*;
import java.util.*;

public class FileChannelService implements ChannelServiece {
    private final String filePath = "channel.ser";
    private Map<UUID, Channel> channels =  new HashMap<>();

    @SuppressWarnings("unchecked")
    private void loadChannels() {
        try(FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            channels = (Map<UUID,Channel>)ois.readObject();
        }catch(FileNotFoundException e){
            channels = new HashMap<>();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void saveChannels() {
        try(FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(channels);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addChannel(Channel channel) {
        channels.put(channel.getChannelId(), channel);
        saveChannels();
    }

    @Override
    public Channel readChannel(UUID channelId) {
        loadChannels();
        return channels.get(channelId);
    }

    @Override
    public void updateChannel(Channel channel) {
        loadChannels();
        if(channels.containsKey(channel.getChannelId())) {
            channels.put(channel.getChannelId(), channel);
            saveChannels();
        }
    }

    @Override
    public void deleteChannel(UUID channelId) {
        loadChannels();
        if(channels.containsKey(channelId)) {
            channels.remove(channelId);
            saveChannels();
        }
    }

    @Override
    public List<Channel> readAllChannel() {
        loadChannels();
        return new ArrayList<>(channels.values());
    }
}
