package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class FileChannelRepository implements ChannelRepository {
    private final Path directory = Paths.get("./src/main/resources/ChannelDate");
    private final FileEdit instance = new  FileEdit();

    public FileChannelRepository(){
        instance.init(directory);
    }

    @Override
    public Channel save(Channel channel) {
        instance.save(directory,channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {return instance.load(directory,id);}

    @Override
    public List<Channel> findAll() {
        return instance.loadAll(directory);
    }

    @Override
    public boolean existsById(UUID id) {
        return findById(id).isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        boolean isDelete = instance.delete(directory,id);
        if(!isDelete){
            throw new NullPointerException(" 유저 삭제 실패");
        }
    }

}
