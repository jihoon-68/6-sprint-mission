package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JCFMessageService implements MessageService {
    private final List<Message> messageList = new ArrayList<>();

    @Override
    public Message read(User sender){
        for (Message message : messageList) {
            if(message.getSender().equals(sender.getName())){
                System.out.println(sender.getName() + "이(가) 보낸 메시지: " + message);
                return message;
            }
        }
        System.out.println("보낸 메시지가 없습니다");
        return null;
    }
    @Override
    public Message create(User sender, User reciever, String content, Channel channel){
        Message message = new Message(sender,reciever,content,channel);
        messageList.add(message);
        return message;
    }
    @Override
    public List<Message> allRead(){
        return messageList;
    }
    @Override
    public void modify(String content){
        Scanner sc = new Scanner(System.in);
        for (Message message : messageList) {
            if(message.getContent().equals(content)){
                System.out.println("메시지 수정 입력: ");
                String msg = sc.nextLine();
                message.setContent(msg);
                System.out.println("채널명이 수정되었습니다: " + msg);
            }
        }
    }
    @Override
    public void delete(String content){
        for (Message message : messageList) {
            if(message.getContent().equals(content)){
                messageList.remove(message);
                break;
            }
        }
    }
}
