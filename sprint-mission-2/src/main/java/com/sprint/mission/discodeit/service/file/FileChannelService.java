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
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/ChannelDate");
    private static final FileEdit instance = new FileEdit();;

    private Path filePath(Channel channel) {
        return directory.resolve(channel.getChannelId().toString().concat(".ser"));
    }

    public FileChannelService() {
        instance.init(directory);
    }

    public Channel createChannel(String name, String root){
        Channel newChannel = new Channel(name, root);
        instance.save(directory, newChannel);
        return newChannel;
    };

    public Channel findChannelById(UUID id){
        List<Channel> channels = instance.load(directory);
        return channels.stream()
                .filter(ch -> ch.getChannelId().equals(id))
                .toList()
                .get(0);
    };
    public List<Channel> findAllChannels(){
        return instance.load(directory);
    };
    public void updateChannel(Channel channel){
        instance.save(filePath(channel), channel);
    };
    public void deleteChannel(UUID id){
        Channel channel = findChannelById(id);
        boolean isDelete = instance.delete(filePath(channel));
        if(isDelete){
            System.out.println(channel.getChannelId()+" 유저 삭제 완료");
        }else {
            System.out.println(channel.getChannelId()+" 유저 삭제 실패");
        }
    };

    public void addUserToChannel(Channel channel, User user){};
    public void removeUserFromChannel(Channel channel, User user){};
    public void addMessageToChannel(Channel channel, Message message){};
    public void removeMessageFromChannel(Channel channel, Message message){};

}
