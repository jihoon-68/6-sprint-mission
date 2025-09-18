package com.sprint.mission.discodeit.service;

<<<<<<< HEAD
import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;
=======
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
>>>>>>> 박지훈

import java.util.List;
import java.util.UUID;

public interface MessageService {
<<<<<<< HEAD
    Message create(CreateMessageDTO createMessageDTO);
    Message find(UUID id);
    List<Message> findAllByChannelId(UUID channelId);
    void update(UpdateMessageDTO updateMessageDTO);
    void delete(UUID id);
=======
      Message createMessage(User sender, String message);
      Message findMessageById(UUID id);
      List<Message> findAllMessages();
      void updateMessage(Message message);
      void deleteMessage(UUID id);
>>>>>>> 박지훈
}
