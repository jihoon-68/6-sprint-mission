package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.utils.FileProcessType;
import com.sprint.mission.discodeit.utils.FileUtil;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileChannelRepository implements ChannelRepository {

    private final String fileName = "channel.ser";
    @Override
    public boolean save(Channel channel) {
        return FileUtil.getInstance().save(fileName,channel.getId(),channel);
    }

    @Override
    public Map<UUID, Channel> getAllChannels() {

        Map<UUID, Channel> map = FileUtil.getInstance().getDataFromFile(fileName, UUID.class, Channel.class);
        if(map == null){
            System.out.println("Error reading file");
            return null;
        }

        return map.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean delete(UUID channelId) {
        if(channelId == null){
            System.out.println("UUID is null");
            return false;
        }

        return FileUtil.getInstance().updateDeleteProcess(fileName,channelId,FileProcessType.Delete,Channel.class,null);
    }

    @Override
    public boolean update(Channel channel) {
        if(channel == null){
            System.out.println("channel is null");
            return false;
        }

        return FileUtil.getInstance().updateDeleteProcess(fileName,channel.getId(),FileProcessType.Update,Channel.class,channel);
    }

    @Override
    public void deleteAll() {
        FileUtil.getInstance().deleteAll(fileName);
    }
}


