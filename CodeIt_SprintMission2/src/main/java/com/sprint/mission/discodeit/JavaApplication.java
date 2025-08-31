package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;

import java.util.UUID;


public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();


        // UserService domain test
        System.out.println("[UserService domain test]");
        System.out.println();

        //   create
        User u1 = userService.create(new User("강아지", "cat@example.com"));
        User u2 =userService.create(new User("고양이", "dog@example.com"));
        User u3 =userService.create(new User("비둘기", "99@example.com"));
        User u4 =userService.create(new User("호랑이", "tiger@example.com"));

        // readAll
        System.out.println("<ReadAll>");
        for (User user : userService.readAll()){
            System.out.println(user);
        }

        System.out.println();

        // read
        System.out.println("<Read>");
        UUID readUserId = UUID.fromString(u1.getId().toString());
        System.out.println(userService.read(readUserId));

        System.out.println();

        // update
        userService.update(u1.getId(),"돼지","pig.example.com");

        System.out.println("<Update>");
        for (User user : userService.readAll()){
            System.out.println(user);
        }

        System.out.println();


        System.out.println();

        // delete
        userService.delete(u1.getId());

        System.out.println("<Delete>");
        for (User user : userService.readAll()){
            System.out.println(user);
        }

        System.out.println("-----------------------------------------------------------");

        // -------------------------------------------------------

        ChannelService channelService = new JCFChannelService();


        // UserService domain test
        System.out.println("[ChannelService domain test]");
        System.out.println();

        //   create
        Channel c1 = channelService.create(new Channel("공지", "다양한 공지를 업로드 합니다."));
        Channel c2 = channelService.create(new Channel("자유게시판", "스프린터들과 자유롭게 이야기를 나누어 봐요."));
        Channel c3 = channelService.create(new Channel("강의자료", "매일마다 새로운 강의자료를 업데이트해요."));
        Channel c4 = channelService.create(new Channel("스터디", "새로운 스터디를 모집해 보세요."));

        // readAll
        System.out.println("<ReadAll>");
        for (Channel channel : channelService.readAll()){
            System.out.println(channel);
        }

        System.out.println();

        // read
        System.out.println("<Read>");
        UUID readChannelId = UUID.fromString(c1.getId().toString());
        System.out.println(channelService.read(readChannelId));

        System.out.println();

        // update
        channelService.update(c1.getId(),"행정","행정과 관련된 내용이에요.");

        System.out.println("<Update>");
        for (Channel channel : channelService.readAll()){
            System.out.println(channel);
        }

        System.out.println();


        System.out.println();

        // delete
        channelService.delete(c1.getId());

        System.out.println("<Delete>");
        for (Channel channel : channelService.readAll()){
            System.out.println(channel);
        }

        System.out.println("-----------------------------------------------------------");

        // -------------------------------------------------------

        MessageService messageService = new JCFMessageService();


        // UserService domain test
        System.out.println("[MessageService domain test]");
        System.out.println();

        //   create
        Message m1 = messageService.create(new Message("코코", "좋은 하루 보내~","푸푸"));
        Message m2 = messageService.create(new Message("모모", "내일 점심 같이 먹자~","치치"));

        // readAll
        System.out.println("<ReadAll>");
        for (Message message : messageService.readAll()){
            System.out.println(message);
        }

        System.out.println();

        // read
        System.out.println("<Read>");
        UUID readMessageId = UUID.fromString(m1.getId().toString());
        System.out.println(messageService.read(readMessageId));

        System.out.println();

        // update
        messageService.update(m1.getId(),"머그","수정할게!","푸푸");

        System.out.println("<Update>");
        for (Message message : messageService.readAll()){
            System.out.println(message);
        }

        System.out.println();


        System.out.println();

        // delete
        messageService.delete(m1.getId());

        System.out.println("<Delete>");
        for (Message message : messageService.readAll()){
            System.out.println(message);
        }
        
    }
}

