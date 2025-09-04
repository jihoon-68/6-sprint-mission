package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;


import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileMessageService implements MessageService {
    private static final Path directory = Paths.get("./src/main/resources/MessagesDate");
    private static final FileEdit instance = new FileEdit();;

    private Path filePaths(Message message) {
        return directory.resolve(message.getId().toString() + ".ser");
    }

    public FileMessageService() {
        instance.init(directory);
    }

    public Message createMessage(User sender, String message){
        Message newMessage = new Message(sender, message);
        instance.save(filePaths(newMessage), newMessage);
        return newMessage;
    };
    public Message findMessageById(UUID id){
        List<Message> messagesList = instance.load(directory);
        return messagesList.stream()
                .filter(message -> message.getId().equals(id))
                .findAny()
                .orElse(null);
    };
    public List<Message> findAllMessages(){
        List<Message> messagesList = instance.load(directory);
        if(messagesList.isEmpty()){
            throw new NullPointerException("현재 메시지없음");
        }
        return instance.load(directory);
    };
    public void updateMessage(Message message){
        instance.save(filePaths(message), message);
    };
    public void deleteMessage(UUID id){
        Message message = this.findMessageById(id);
        boolean isDelete = instance.delete(filePaths(message));
        if(!isDelete){
            throw new NullPointerException(message.getId()+" 유저 삭제 실패");
        }

    };

}
