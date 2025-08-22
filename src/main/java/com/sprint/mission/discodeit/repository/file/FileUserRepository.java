package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.utils.FileProcessType;
import com.sprint.mission.discodeit.utils.FileUtil;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUserRepository implements UserRepository{

    private final String fileName = "user.ser";
    @Override
    public boolean save(User user) {
        return FileUtil.getInstance().save(fileName,user.getId(),user);
    }

    @Override
    public boolean delete(User user) {
        return deleteUpdateProcess(user, FileProcessType.Delete);
    }

    @Override
    public boolean update(User user) {
        return deleteUpdateProcess(user, FileProcessType.Update);
    }

    private boolean deleteUpdateProcess(User user, FileProcessType type){
        if(user == null){
            System.out.println("user is null");
            return false;
        }

        if(type == FileProcessType.Delete)
            return FileUtil.getInstance().updateDeleteProcess(fileName,user.getId(), type, User.class,null);
        else
            return FileUtil.getInstance().updateDeleteProcess(fileName,user.getId(),type,User.class,user);
    }

    @Override
    public Map<UUID, User> getAllUsers()
    {
        Map<UUID, User> map = FileUtil.getInstance().getDataFromFile(fileName, UUID.class, User.class);
        if(map == null){
            System.out.println("Error reading file");
            return null;
        }

        return map.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteAll() {
        FileUtil.getInstance().deleteAll(fileName);
    }
}
