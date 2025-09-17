package com.sprint.mission.repository.file;

import com.sprint.mission.dto.channel.ChannelCreateDto;
import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.entity.Channel;
import com.sprint.mission.entity.User;
import com.sprint.mission.repository.ChannelRepository;
import com.sprint.mission.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileChannelRepository extends SaveAndLoadCommon<Channel> implements ChannelRepository {

    public FileChannelRepository() {
        super(Channel.class);
    }

    @Override
    public Channel save(ChannelCreateDto channelCreateDto) {
        if(channelCreateDto.getChannelMembers().isEmpty()){
            Channel channel = new Channel(channelCreateDto.getChannelName(), channelCreateDto.getChannelDescription());
            save(channel);
            return channel;
        } else {
            UserRepository userRepository = new FileUserRepository();
            List<User> members = channelCreateDto.getChannelMembers().stream()
                    .map(userRepository::findByUserName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            Channel channel = new Channel(channelCreateDto.getChannelName(), channelCreateDto.getChannelDescription(), members);
            save(channel);
            return channel;
        }
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        if(load(id).isEmpty()) return Optional.empty();
        return load(id);
    }

    @Override
    public List<Channel> findAll() {
        return loadAll();
    }

    @Override
    public boolean existsById(UUID id) {
        return load(id).isPresent();
    }

    @Override
    public void deleteById(UUID id) {
        delete(id);
    }
}
