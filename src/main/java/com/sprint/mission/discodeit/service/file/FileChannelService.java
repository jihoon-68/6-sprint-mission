package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileChannelService implements ChannelService {
    private static final Path directory = Paths.get("./src/main/resources/ChannelDate");
    private static final FileEdit instance = new FileEdit();;

    private Path filePath(Channel channel) {
        return directory.resolve(channel.getChannelId().toString().concat(".ser"));
    }

    public FileChannelService() {
        instance.init(directory);
    }

    public Channel createChannel(String name, User root){
        Channel newChannel = new Channel(name, root);
        instance.save(filePath(newChannel), newChannel);
        return newChannel;
    };

    public Channel findChannelById(UUID id){
        List<Channel> channels = instance.load(directory);
        return channels.stream()
                .filter(ch -> ch.getChannelId().equals(id))
                .findAny()
                .orElse(null);
    };
    public List<Channel> findAllChannels(){
        List<Channel> channels = instance.load(directory);
        if (channels.isEmpty()){
            throw new NullPointerException("현재 채널없음");
        }
        return instance.load(directory);
    };
    public void updateChannel(Channel channel){

        instance.save(filePath(channel), channel);
    };
    public void deleteChannel(UUID id){
        Channel channel = findChannelById(id);
        boolean isDelete = instance.delete(filePath(channel));
        if(!isDelete){
            throw new NullPointerException(channel.getChannelId()+" 유저 삭제 완료");
        }
    };

    public void addUserToChannel(Channel channel, User user){};
    public void removeUserFromChannel(Channel channel, User user){};
    public void addMessageToChannel(Channel channel, Message message){};
    public void removeMessageFromChannel(Channel channel, Message message){};

}
