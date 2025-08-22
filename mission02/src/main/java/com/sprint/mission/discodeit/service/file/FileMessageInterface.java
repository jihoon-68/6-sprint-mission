package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageInterface extends SaveAndLoadCommon implements MessageService, Serializable {
    Path path = Paths.get(System.getProperty("user.dir"), "messages");
    public FileMessageInterface() {
        init(path);
    }

    @Override
    public void addMessage(Message message) {
        Path filePath = path.resolve(message.getUuid().toString().concat(".ser"));
        save(filePath, message);
    }

    @Override
    public void removeMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("메시지 정보를 입력해주십시오");
        }
        try{
            if(Files.exists(path)){
                Files.delete(path.resolve(message.getUuid().toString().concat(".ser")));
                System.out.println("메세지 삭제 완료: " + message.getUuid().toString());
            } else {
                throw new NotFoundException("메세지가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw new NotFoundException("메세지가 존재하지 않습니다.");
        }
    }

    @Override
    public List<Message> findAllMessages() {
        System.out.println("모든 메시지 조회");
        List<Message> messages = load(path);
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("메시지가 존재하지 않습니다.");
        }
        return messages;
    }

    @Override
    public Message findMessageById(UUID id) {
        List<Message> messages = load(path);
        if (messages == null || messages.isEmpty()) {
            throw new RuntimeException("메시지가 존재하지 않습니다.");
        }
        return messages.stream()
                .filter(message -> message.getUuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("메시지가 존재하지 않습니다."));
    }

    @Override
    public List<Message> findMessagesBySender(UUID senderId) {
        if (senderId == null) {
            throw new IllegalArgumentException("보낸사람을 입력해주십시오");
        }
        List<Message> messages = load(path);
        List<Message> result = messages.stream()
                .filter(message -> message.getSender().equals(senderId))
                .toList();
        if (result.isEmpty()) {
            throw new RuntimeException("보낸 메세지가 없습니다.");
        }
        return result;
    }

    @Override
    public List<Message> findMessagesByReceiver(UUID receiverId) {
        if (receiverId == null) {
            throw new IllegalArgumentException("받는사람을 입력해주십시오");
        }
        List<Message> messages = load(path);
        List<Message> result = messages.stream()
                .filter(message -> message.getReceiver().equals(receiverId))
                .toList();
        if (result.isEmpty()) {
            throw new RuntimeException("받은 메세지가 없습니다.");
        }
        return result;
    }

    @Override
    public void updateMessage(UUID id, String newMessageContext) {
        if (id == null || newMessageContext == null || newMessageContext.isEmpty()) {
            throw new IllegalArgumentException("메시지 ID와 새로운 메시지 내용을 입력해주십시오");
        }
        List<Message> messages = load(path);
        Message message = messages.stream()
                .filter(msg -> msg.getUuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("메시지가 존재하지 않습니다."));
        message.setMessageContext(newMessageContext);
        addMessage(message); // Save the updated message
        System.out.println("메시지 업데이트 완료: " + id.toString());
    }
}

