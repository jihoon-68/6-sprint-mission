package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUser implements UserService {

    private final List<User>userData = new ArrayList<>();

    public void createUser(String username, int age , String email) {
        User newUser =new User(username,age,email);
        userData.add(newUser);
    };
    public User findUserByUserEmail(String userEmail) {
        User target = null;
        for (User user : userData) {
            if (user.getEmail().equals(userEmail)) {
                target = user;
                break;
            }
        }
        return target;
    }

    public User findUserById(UUID id){
        User target = null;
        for (User user : userData){
            if(user.getUserId().equals(id)){
                target = user;
                break;
            }
        }
        return target;
    };

    public List<User> findAllUsers(){
        return userData;
    };

    public User updateUser(User user){
        int idx = userData.indexOf(user);
        if(idx == -1){
            System.out.println("사용자를 찾을수 없습니다.");
            return null;
        }
        userData.set(idx, user);
        return user;
    };

    public void deleteUser(UUID id){
        User user = findUserById(id);
        if(user == null){
            System.out.println("유저를 삭제 못 했습니다.");
            return;
        }
        userData.remove(user);
        System.out.println("유저를 삭제 했습니다");
    };
}
