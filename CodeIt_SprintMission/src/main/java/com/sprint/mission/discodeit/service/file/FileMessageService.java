package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class FileMessageService implements MessageService {

    private final File file;

    public FileMessageService(String filename) {
        this.file = new File(filename);
        if (!file.exists()) {
            saveData(new HashMap<>());
        }
    }

    public void reset() {
        saveData(new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, Message> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, Message>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("불러오기 실패", e);
        }
    }

    private void saveData(Map<UUID, Message> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public Message create(Message message) {
        Map<UUID, Message> data = loadData();
        data.put(message.getId(), message);
        saveData(data);
        return message;
    }

    @Override
    public Message read(UUID id) {
        return loadData().get(id);
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(loadData().values());
    }

    @Override
    public Message update(UUID id, String senderName, String messageContent, String receiverName) {
        Map<UUID, Message> data = loadData();
        Message msg = data.get(id);
        if (msg != null) {
            msg.setSenderName(senderName);
            msg.setMessageContent(messageContent);
            msg.setReciverName(receiverName);
            saveData(data);
        }
        return msg;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, Message> data = loadData();
        if (data.remove(id) != null) {
            saveData(data);
            return true;
        }
        return false;
    }
}
