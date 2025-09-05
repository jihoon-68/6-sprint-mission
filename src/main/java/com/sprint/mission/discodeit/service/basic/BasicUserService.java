package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService{
    private final UserRepository userRepository;

    @Override
    public User create(String username, int age, String email){

        return userRepository.save(new User(username,age,email));
    }

    @Override
    public User find(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("User not found"));
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(userRepository.findAll());
    }

    @Override
    public void update(User user) {
        User userUpdate = find(user.getId());
        userUpdate.update(user.getUsername(),userUpdate.getEmail(),user.getPassword());
        userRepository.save(userUpdate);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
