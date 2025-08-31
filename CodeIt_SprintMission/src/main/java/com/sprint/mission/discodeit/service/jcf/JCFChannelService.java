package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    public JCFChannelService(){
        this.data = new LinkedHashMap<>();
    }

    @Override
    public Channel create(Channel channel){
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Channel read(UUID id){
        if (data.containsKey(id)){
            return data.get(id);
        }
        else{
            System.out.println("해당 ID에 맞는 채널이 없습니다");
            return null;
        }
    }

    @Override
    public List<Channel> readAll(){
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel update(UUID id, String channelName, String channelDescription) {
        Channel chaneel = data.get(id);
        if (chaneel != null) {
            chaneel.setChannelName(channelName);
            chaneel.setChannelDescription(channelDescription);
        }
        return chaneel;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }


}
