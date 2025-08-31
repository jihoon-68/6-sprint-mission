package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;

import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.ChannelService;

import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileChannelService;


public class JavaApplication_FileTest {
    public static void main(String[] args) {


        FileUserService userService = new FileUserService("users.ser");
        userService.reset();

        // CREATE
        User u1 = userService.create(new User("강아지", "dog@example.com"));
        User u2 = userService.create(new User("고양이", "cat@example.com"));

        System.out.println("[CREATE]");
        System.out.println(userService.readAll()); // 전체 유저 출력

        // READ
        System.out.println("[READ]");
        System.out.println(userService.read(u1.getId()));

        // UPDATE
        System.out.println("[UPDATE]");
        userService.update(u2.getId(), "야옹이", "meow@example.com");
        System.out.println(userService.read(u2.getId()));

        // READ ALL
        System.out.println("[READ ALL]");
        System.out.println(userService.readAll());

        // DELETE
        System.out.println("[DELETE]");
        userService.delete(u1.getId());
        System.out.println(userService.readAll());

        FileMessageService messageService = new FileMessageService("messages.ser");
        messageService.reset();


        // CREATE
        Message m1 = messageService.create(new Message("모모", "점심 먹었어??", "치치"));
        Message m2 = messageService.create(new Message("치치", "네! 먹었어요~", "모모"));

        // READ
        System.out.println("READ: " + messageService.read(m1.getId()));

        // UPDATE
        messageService.update(m2.getId(), "모모", "아, 저녁 같이 먹을까?", "치치");

        // READ ALL
        System.out.println("READ ALL: " + messageService.readAll());

        // DELETE
        messageService.delete(m1.getId());
        System.out.println("AFTER DELETE: " + messageService.readAll());

        // ----------------------------------------

        FileChannelService channelService = new FileChannelService("channels.ser");
        channelService.reset();

        // CREATE
        Channel c1 = channelService.create(new Channel("일반", "일반 대화 채널"));
        Channel c2 = channelService.create(new Channel("공지", "공지사항 채널"));

        System.out.println("[CREATE]");
        System.out.println(channelService.readAll());

        // READ
        System.out.println("[READ]");
        System.out.println(channelService.read(c1.getId()));

        // UPDATE
        System.out.println("[UPDATE]");
        channelService.update(c2.getId(), "공지사항", "중요 공지사항 채널");
        System.out.println(channelService.read(c2.getId()));

        // DELETE
        System.out.println("[DELETE]");
        channelService.delete(c1.getId());
        System.out.println(channelService.readAll());


    }
}

