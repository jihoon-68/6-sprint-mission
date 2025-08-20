package com.sprint.mission.discodeit.serviece.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.serviece.ChannelServiece;
import com.sprint.mission.discodeit.serviece.MessageServiece;
import com.sprint.mission.discodeit.serviece.UserServiece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessageServiece implements MessageServiece {
  private final List<Message>  messages;
  private final UserServiece userServiece;
  private final ChannelServiece channelServiece;

  public JCFMessageServiece(UserServiece userServiece, ChannelServiece channelServiece) {
    this.messages = new ArrayList<>();
    this.userServiece = userServiece;
    this.channelServiece = channelServiece;
  }

  @Override
  public void createMessage(Message message, UUID userId,UUID channelId) {
    User user = userServiece.readUser(userId);
    Channel channel = channelServiece.readChannel(channelId);
    if(user == null){
      throw new IllegalArgumentException("User not found");
    }else if(channel == null){
      throw new IllegalArgumentException("Channel not found");
    }
    message.setUser(user);
    message.setChannel(channel);

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
