package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final List<Message> messages;

    public JCFMessageService() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void createMessage(UUID authorId, UUID channelId, String content) {
        if (content.trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }

        Message message = new Message(authorId, channelId, content);

        messages.add(message);
        System.out.println("[Info] 메시지가 생성되었습니다.");
    }

    @Override
    public void updateContent(String content, UUID id) {
        if (content.trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }

        Message existMessage = findMessageById(id);

        if (existMessage != null) {
            existMessage.updateContent(content);
            System.out.println("[Info] 메시지 업데이트가 완료되었습니다.");
        } else {
            System.out.println("[Error] 메시지 업데이트에 실패했습니다.");
        }
    }

    @Override
    public void deleteMessage(UUID id) {
        Message existMessage = findMessageById(id);

        if (existMessage != null) {
            messages.remove(existMessage);
            System.out.println("[Info] 메시지가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 메시지 삭제에 실패했습니다.");
        }
    }

    @Override
    public List<Message> findAllMessages() {
        return messages;
    }

    @Override
    public Message findMessageById(UUID id) {
        return messages.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Message> findMessagesByAuthorIdAndChannelId(UUID authorId, UUID channelId) {
        return  messages.stream().filter(m -> m.getChannelId().equals(channelId) && m.getAutherId().equals(authorId)).toList();
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return messages.stream().filter(m -> m.getChannelId().equals(channelId)).toList();
    }
}
