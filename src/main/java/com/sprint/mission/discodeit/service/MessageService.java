package com.sprint.mission.discodeit.service;

<<<<<<< HEAD
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
=======
import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;
>>>>>>> ff6aee37135da2c11de96095adcd9502ced596ab

import java.util.List;
import java.util.UUID;

public interface MessageService {
<<<<<<< HEAD
      Message createMessage(User sender, String message);
      Message findMessageById(UUID id);
      List<Message> findAllMessages();
      void updateMessage(Message message);
      void deleteMessage(UUID id);
=======
    Message create(CreateMessageDTO createMessageDTO);
    Message find(UUID id);
    List<Message> findAllByChannelId(UUID channelId);
    void update(UpdateMessageDTO updateMessageDTO);
    void delete(UUID id);
>>>>>>> ff6aee37135da2c11de96095adcd9502ced596ab
}
