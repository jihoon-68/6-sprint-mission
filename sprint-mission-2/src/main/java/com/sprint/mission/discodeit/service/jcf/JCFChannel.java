package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannel  implements ChannelService {

    private final List<Channel> channelData;
    private final JCFUser jcfUser;
    private final JCFMessage jcfMessage;

    public JCFChannel(JCFUser jcfUser,JCFMessage jcfMessage){
        this.channelData = new ArrayList<>();
        this.jcfUser = jcfUser;
        this.jcfMessage = jcfMessage;
    }

    public Channel createChannel(String name, String root) {
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

    public void updateChannel(Channel channel){
        int idx = channelData.indexOf(channel);
        if(idx == -1){
            System.out.println("채널을 찾을수 없습니다");
            return;
        }
        channelData.set(idx, channel);
    };

    public void deleteChannel(UUID id){
        Channel channel = findChannelById(id);
        if(channel == null){
            System.out.println("채널을 찾을수 없습니다");
            return;
        }
        channelData.remove(channel);
        System.out.println("체널을 삭제 했습니다");
    };

    //서버 유저 추가
    public void addUserToChannel(Channel channel, User user){
        channel.updateChanelUsers(user);
        this.updateChannel(channel);
    };

    public void removeUserFromChannel(Channel channel, User user){
        channel.getChannelUsers().remove(user);
        this.updateChannel(channel);
    };


    //서버 메새지 추가
    public void addMessageToChannel(Channel channel, Message message){
        channel.updateChanelMessages(message);
        this.updateChannel(channel);
    };

    public void removeMessageFromChannel(Channel channel, Message message){
        channel.getChannelMessages().remove(message);
        this.updateChannel(channel);
    };

}
