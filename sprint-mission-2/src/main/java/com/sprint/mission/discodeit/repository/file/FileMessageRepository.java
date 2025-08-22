package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/ChannelDate");
    private static final FileEdit instance = new  FileEdit();

    private Path filePaths(Message message) {

        return directory.resolve(message.getMessageId().toString() + ".ser");
    }
    public FileMessageRepository() {
        instance.init(directory);
    }

    public Message createMessage(User sender, String message) {
        Message newMessage = new Message(sender, message);
        instance.save(filePaths(newMessage), newMessage);
        return newMessage;
    }

    public Message findMessageById(UUID id) {
        List<Message> messageList = instance.load(directory);
        return messageList.stream()
                .filter(message -> message.getMessageId().equals(id))
                .toList()
                .get(0);
    }

    public List<Message> findAllMessages() {
        return instance.load(directory);
    }

    public void updateMessage(Message message) {
        instance.save(filePaths(message), message);
    }

    public void deleteMessage(UUID id) {
        instance.delete(filePaths(findMessageById(id)));
    }
}
