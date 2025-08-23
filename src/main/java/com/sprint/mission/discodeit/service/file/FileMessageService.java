package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class FileMessageService implements MessageService {
    private final MessageRepository messageRepository;

    public FileMessageService() {
        this.messageRepository = new FileMessageRepository();
    }


    @Override
    public void createMessage(UUID authorId, UUID channelId, String content) {
        if (content.trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }

        Message message = new Message(authorId, channelId, content);

        messageRepository.save(message);

        System.out.println("[Info] 메시지가 생성되었습니다.");
    }

    @Override
    public void updateContent(String content, UUID id) {
        if (content.trim().isEmpty()) {
            System.out.println("[Error] 메시지는 1글자 이상 입력해주세요.");
            return;
        }

        Message message = messageRepository.findById(id);

        if (message != null) {
            message.updateContent(content);
            messageRepository.save(message);

            System.out.println("[Info] 메시지 업데이트가 완료되었습니다.");
        } else {
            System.out.println("[Error] 메시지 업데이트에 실패했습니다.");
        }
    }

    @Override
    public void deleteMessageById(UUID id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            System.out.println("[Info] 메시지가 삭제되었습니다.");
        } else {
            System.out.println("[Error] 메시지 삭제에 실패했습니다.");
        }
    }

    @Override
    public void deleteMessageByAuthorId(UUID authorId) {
        List<UUID> ids = messageRepository.findAll()
                .stream().filter(m -> m.getAuthorId().equals(authorId))
                .map(m -> m.getId()).toList();

        for (UUID id : ids) {
            messageRepository.deleteById(id);
        }
    }


    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message findMessageById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findMessagesByAuthorIdAndChannelId(UUID authorId, UUID channelId) {
        return  messageRepository.findByAuthorIdAndChannelId(authorId, channelId);
    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {
        return messageRepository.findByChannelId(channelId);
    }
}
