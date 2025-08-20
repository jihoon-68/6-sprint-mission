package com.sprint.mission.discodeit.serviece.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.serviece.UserServiece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserServiece implements UserServiece {
  private final List<User> users;

  public JCFUserServiece() {
    this.users = new ArrayList<>();
  }

  @Override
  public User getUser(UUID userId) {
    for(User user : users){
      if(user.getUserId().equals(userId)){
        return user;
      }
    }
    return null;
  }

  @Override
  public void createUser(User user) {
    users.add(user);
  }

  @Override
  public User readUser(UUID userId) {
    return users.stream()
        .filter(u->u.getUserId().equals(userId))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void updateUser(User user) {
    for(User u : users){
      if(u.getUserId().equals(user.getUserId())){
        if(user.getNickName() != null){
          u.updateNickName(user.getNickName());
        } else if (user.getPassWord() != null) {
          u.updatePassWord(user.getPassWord());
        }
        return;
      }
    }
  }

  @Override
  public void deleteUser(UUID userId) {
    users.removeIf(u -> u.getUserId().equals(userId));
  }

  @Override
  public List<User> readAllInfo() {
    return new ArrayList<>(users);
  }
}
