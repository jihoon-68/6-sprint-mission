package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {

    private List<Message> data = new ArrayList<>();

    @Override
    public void save(Message message) {
        data.removeIf(m -> m.getId().equals(message.getId()));
        data.add(message);
    }

    // 단건 조회 (ID로 조회, 관리자용)
    @Override
    public Message findById(UUID id) {
        return data.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // 채널별 메시지 조회
    @Override
    public List<Message> findByChannelId(UUID channelId) {
        return data.stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public List<Message> findAllByIdIn(List<UUID> ids) {
        return data.stream()
                .filter(message -> ids.contains(message.getId()))
                .toList();
    }

    @Override
    public void delete(Message message) {
        boolean removed = data.removeIf(m-> m.getId().equals(message.getId()));
        if (!removed) {
            throw new NotFoundException("존재하지 않는 메시지입니다. id=" + message.getId());
        }
    }

    @Override
    public void clear(){
        data.clear();
    }

}
