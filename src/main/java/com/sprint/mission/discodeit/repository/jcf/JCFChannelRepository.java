package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    private List<Channel> data = new ArrayList<>();

    @Override
    public void save(Channel channel) {
        data.removeIf(c -> c.getId().equals(channel.getId()));
        data.add(channel);
    }

    @Override
    public Channel findById(UUID id) {
        return data.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // 채널 전체 조회
    @Override
    public List<Channel> findAll() {
        return List.copyOf(data);
    }

    @Override
    public void delete(Channel channel) {
        boolean removed = data.removeIf(c -> c.getId().equals(channel.getId()));
        if (!removed) {
            throw new NotFoundException("존재하지 않는 채널입니다. id=" + channel.getId());
        }
    }

    @Override
    public void clear(){
        data.clear();
    }

}
