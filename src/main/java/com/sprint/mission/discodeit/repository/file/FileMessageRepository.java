package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.utils.FileProcessType;
import com.sprint.mission.discodeit.utils.FileUtil;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileMessageRepository implements MessageRepository {

    private final String fileName = "message.ser";
    @Override
    public void save(Message message) {
        FileUtil.getInstance().save(fileName,message.getId(),message);
    }

    @Override
    public Map<UUID, Message> getMessages() {
        Map<UUID, Message> map = FileUtil.getInstance().getDataFromFile(fileName, UUID.class, Message.class);
        if(map == null){
            System.out.println("Error reading file");
            return null;
        }

        return map.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean delete(Message message) {
        return deleteUpdateProcess(message, FileProcessType.Delete);
    }

    @Override
    public boolean update(Message message) {

        return deleteUpdateProcess(message, FileProcessType.Update);
    }

    private boolean deleteUpdateProcess(Message message, FileProcessType type){
        if(message == null){
            System.out.println("message is null");
            return false;
        }

        if(type == FileProcessType.Delete)
            return FileUtil.getInstance().updateDeleteProcess(fileName,message.getId(), type, Message.class,null);
        else
            return FileUtil.getInstance().updateDeleteProcess(fileName,message.getId(),type,Message.class,message);
    }

    @Override
    public void deleteAll() {
        FileUtil.getInstance().deleteAll(fileName);
    }
}
