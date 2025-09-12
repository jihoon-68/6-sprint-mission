package com.sprint.mission.discodeit.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.UserService;

public class FileMessageRepository implements MessageRepository {

    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "messages.ser";
    private Map<UUID, Message> data = new HashMap<>();
    private final UserService userService;

    public FileMessageRepository(UserService userService) {
        this.userService = userService;
        loadFromFile();
    }

    @Override
    public Message createMessage(UUID senderId, UUID receiverId, String contents) {
        if (userService.getUserById(senderId) == null || userService.getUserById(receiverId) == null) {
            System.err.println("Error: Sender or receiver does not exist.");
            return null;
        }
        Message message = new Message(senderId, receiverId, contents);
        data.put(message.getId(), message);
        saveToFile();
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Message updateMessage(UUID id, String newContents) {
        Message message = data.get(id);
        if (message != null) {
            message.updateMessage(newContents);
            saveToFile();
        }
        return message;
    }

    @Override
    public boolean deleteMessage(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving messages to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return; // 파일이 없거나 비어있으면 새로 시작
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            data = (Map<UUID, Message>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading messages from file: " + e.getMessage());
            data = new HashMap<>(); // 오류 발생 시 데이터 맵을 초기화
        }
    }

}
