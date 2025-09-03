package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class FileMessageService implements MessageService {

    private static final String FILE_PATH;

    static {
        try {
            URI fileUri = FileMessageService.class
                    .getClassLoader()
                    .getResource("")
                    .toURI()
                    .resolve("message.ser");
            FILE_PATH = Path.of(fileUri).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message serialization path: " + FILE_PATH);
    }

    private final ChannelService channelService;
    private final UserService userService;

    public FileMessageService(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        channelService.read(channelId).orElseThrow();
        userService.read(authorId).orElseThrow();
        Map<UUID, Message> data = readData();
        Message message = Message.of(content, channelId, authorId);
        data.put(message.id(), message);
        writeData(data);
        return message;
    }

    @Override
    public Optional<Message> read(UUID id) {
        Map<UUID, Message> data = readData();
        Message message = data.get(id);
        return Optional.ofNullable(message);
    }

    @Override
    public Set<Message> readAll() {
        Map<UUID, Message> data = readData();
        return new HashSet<>(data.values());
    }

    @Override
    public Message update(UUID id, String newContent) {
        Map<UUID, Message> data = readData();
        Message updatedMessage = data.compute(id,
                (keyId, valueMessage) -> Objects.requireNonNull(valueMessage).updateContent(newContent));
        writeData(data);
        return updatedMessage;
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Message> data = readData();
        data.remove(id);
        writeData(data);
    }

    private Map<UUID, Message> readData() {
        try (var in = new ObjectInputStream(Files.newInputStream(Path.of(FILE_PATH)))) {
            Object o = in.readObject();
            if (!(o instanceof Map<?, ?> data)) {
                throw new ClassCastException();
            }
            Map<UUID, Message> result = new HashMap<>();
            for (Object e : data.entrySet()) {
                if (!(e instanceof Map.Entry<?, ?> entry)) {
                    throw new ClassCastException();
                }
                if (!(entry.getKey() instanceof UUID id) || !(entry.getValue() instanceof Message message)) {
                    throw new ClassCastException();
                }
                result.put(id, message);
            }
            return result;
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeData(Map<UUID, Message> data) {
        try (var out = new ObjectOutputStream(Files.newOutputStream(Path.of(FILE_PATH)))) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
