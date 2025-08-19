package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannel  implements ChannelService {

    private final List<Channel> channelData = new ArrayList<>();

    public Channel createChannel(String name, User root) {
        Channel channel =new Channel(name, root);
        channelData.add(channel);
        return channel;
    };

    public Channel findChannelById(UUID id){
        Channel target = null;
        for (Channel channel : channelData){
            if(channel.getChannelId().equals(id)){
                target = channel;
            }
        }
        return target;
    };

    public List<Channel> findAllChannels(){
        return channelData;
    };

    public Channel updateChannel(Channel channel){
        int idx = channelData.indexOf(channel);
        if(idx == -1){
            System.out.println("채널을 찾을수 없습니다");
            return null;
        }
        channelData.set(idx, channel);
        return channel;
    };

    public void deleteChannel(UUID id){
        Channel channel = findChannelById(id);
        if(channel == null){
            System.out.println("채널을 찾을수 없습니다");
            return;
        }
        channelData.remove(findChannelById(id));
        System.out.println("체널을 삭제 했습니다");
    };

}
