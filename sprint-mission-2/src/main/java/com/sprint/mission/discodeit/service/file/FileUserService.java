package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileUserService implements UserService {
    private static final Path directory = Paths.get("/Users/mac/IdeaProjects/6-sprint-mission/sprint-mission-2/src/main/resources/UserDate/");
    private static final FileEdit instance = new FileEdit();;

    //클래스 외부에서 접근할 필용가 없는 메소드라서 플라이비 함
    //객체 UUID를 파일 이름으로 정함
    private Path filePath(User user){
        return directory.resolve(user.getUserId().toString().concat(".ser"));
    };

    public FileUserService() {
        instance.init(directory);
    }

    //사용자 별로 다른 파일? 아니면 같은 파일?
    //일단은 같은 파일 진행

    public User createUser(String username, int age , String email){
        User newUser = new User(username, age, email);
        instance.save(filePath(newUser),newUser);
        return newUser;
    };
    public User findUserById(UUID id){
        List<User> users = instance.load(directory);
        return users.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    };
    public User findUserByUserEmail(String userEmail){
        List<User> users = instance.load(directory);
        return users.stream()
                .filter(user -> user.getEmail().equals(userEmail))
                .findAny()
                .orElse(null);
    };
    public List<User> findAllUsers(){
        List<User> users = instance.load(directory);
        if (users.isEmpty()){
            throw new NullPointerException("현재 유저 없음");
        }
        return users;
    };
    public void updateUser(User user){
        instance.save(filePath(user),user);
    };
    public void deleteUser(UUID id){
        User user = findUserById(id);
        boolean isDelete = instance.delete(filePath(user));
        if(!isDelete){
            throw new NullPointerException(user.getEmail()+" 유저 삭제 실패");
        }
    };

}
