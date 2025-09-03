package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;

public class JCFChannelRepository implements ChannelRepository {

    private List<Channel> data = new ArrayList<>();

    @Override
    public void save(Channel ch) {
        data.add(ch);
    }

    // 채널 단건 조회
    @Override
    public Channel findByChannelName(String name) { // 채널 이름이 독립적이라고 가정
        for (Channel channel : data) {
            if (channel.getName().equals(name)) {
                return channel;
            }
        }
        System.out.println("해당 채널이 존재하지 않습니다.");
        return null;
    }

    // 채널 전체 조회
    @Override
    public List<Channel> findAll() {
        if (data.isEmpty()) {
            throw new RuntimeException("채널이 존재하지 않습니다.");
        }
        return data;
    }

    @Override
    public void delete(Channel ch) {
        data.remove(ch);
    }
}
