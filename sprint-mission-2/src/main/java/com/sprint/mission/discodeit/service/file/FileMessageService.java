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
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/MessagesDate");
    private static final FileEdit instance = new FileEdit();;

    private Path filePaths(Message message) {
        return directory.resolve(message.getMessageId().toString() + ".ser");
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
                .filter(message -> message.getMessageId().equals(id))
                .toList()
                .get(0);
    };
    public List<Message> findAllMessages(){
        return instance.load(directory);
    };
    public void updateMessage(Message message){
        instance.save(filePaths(message), message);
    };
    public void deleteMessage(UUID id){
        Message message = this.findMessageById(id);
        boolean isDelete = instance.delete(filePaths(message));
        if(isDelete){
            System.out.println(message.getMessageId()+" 유저 삭제 완료");
        }else {
            System.out.println(message.getMessageId()+" 유저 삭제 실패");
        }

    };

}
