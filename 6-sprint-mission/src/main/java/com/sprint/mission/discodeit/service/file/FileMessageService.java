package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileMessageService implements MessageService {

    Path directory = Paths.get(System.getProperty("user.dir"), "MessageData");

    @Override
    public Message create(User sender, User reciever, String content, Channel channel) {
        FileInitSaveLoad.init(directory);

        Message message = new Message(sender, reciever, content, channel);

        Path filePath = directory.resolve(message.getContent().concat(".ser"));
        FileInitSaveLoad.<Message>save(filePath, message);

        return message;
    }

    @Override
    public Message read(String sender) {
        return allRead()
                .stream()
                .filter(msg -> msg.getSender().equals(sender))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Message> allRead() {
        return FileInitSaveLoad.<Message>load(directory);
    }

    @Override
    public Message modify(UUID id) {
        return allRead()
                .stream()
                .filter(msg -> msg.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public Message delete(UUID id) {
        return allRead()
                .stream()
                .filter(msg -> msg.getCommon().getId().equals(id))
                .findAny()
                .orElse(null);
    }
}
