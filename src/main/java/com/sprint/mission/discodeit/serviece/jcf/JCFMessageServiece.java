package com.sprint.mission.discodeit.serviece.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.serviece.MessageServiece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageServiece implements MessageServiece {
  private final List<Message>  messages;

  public JCFMessageServiece() {
    this.messages = new ArrayList<>();
  }

  @Override
  public void createMessage(Message message){
    messages.add(message);
  }

  @Override
  public Message readMessage(UUID messageId) {
    return messages.stream()
        .filter(message -> message.getMessageId().equals(messageId))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void updateMessage(Message message) {
    for(Message m : messages) {
      if(m.getMessageId().equals(message.getMessageId())) {
        m.updateText(message.getText());
        return;
      }
    }
  }

  @Override
  public void deleteMessage(UUID messageId) {
    messages.removeIf(message -> message.getMessageId().equals(messageId));
  }

  @Override
  public List<Message> readAllMessage() {
    return new ArrayList<>(messages);
  }

}
