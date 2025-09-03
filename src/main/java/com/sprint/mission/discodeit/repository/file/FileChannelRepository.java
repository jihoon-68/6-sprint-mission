package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final String filePath = "file.rep";
    private Map<UUID, Channel> channels = new HashMap<>();

    @SuppressWarnings("unchecked")
    private void loadChannels(){
        try(FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            channels = (Map<UUID,Channel>) ois.readObject();
        }catch (FileNotFoundException e){
            channels = new HashMap<>();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private void saveChannels(){
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
    }

    @Override
    public Channel readChannel(UUID channelId) {
        return channels.get(channelId);
    }

    @Override
    public void deleteChannel(UUID channelId) {
        channels.remove(channelId);
    }

    @Override
    public void updateChannel(Channel channel) {
        if(channels.containsKey(channel.getChannelId())) {
            channels.put(channel.getChannelId(), channel);
        }else{
            throw new IllegalArgumentException("Channel does not exist");
        }
    }

    @Override
    public List<Channel> readAllChannel() {
        return new ArrayList<>(channels.values());
    }
}
