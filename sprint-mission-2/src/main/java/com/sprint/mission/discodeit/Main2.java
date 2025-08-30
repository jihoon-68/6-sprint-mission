package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;


public class Main2 {

    static User setupUser(BasicUserService basicUserService) {
        return basicUserService.create("woody",20 ,"woody@codeit.com");
    }

    static Channel setupChannel(BasicChannelService basicChannelService,User user) {
        return basicChannelService.create("공지", user);
    }

    static void messageCreateTest(BasicMessageService basicMessageService, Channel channel, User author) {
        Message message = basicMessageService.create(channel,author,"안녕하세요");
        System.out.println("메시지 생성: " + message.getMessageId());
    }

    public static void main(String[] args) {


        //JCF 저장 방식
        JCFUserRepository jcfUserRepository = new JCFUserRepository();
        JCFChannelRepository jcfChannelRepository = new JCFChannelRepository();
        JCFMessageRepository jcfMessageRepository = new JCFMessageRepository();

        BasicUserService JCFbasicUserService = new BasicUserService(jcfUserRepository);
        BasicChannelService JCFbasicChannelService = new BasicChannelService(jcfChannelRepository);
        BasicMessageService JCFbasicMessageService = new BasicMessageService(jcfMessageRepository,jcfChannelRepository);


        User JCFUUser = setupUser(JCFbasicUserService);
        Channel JCFUChannel = setupChannel(JCFbasicChannelService,JCFUUser);
        // 테스트
        messageCreateTest(JCFbasicMessageService, JCFUChannel, JCFUUser);


        //File 저장 방식
        FileUserRepository fileUserRepository = new FileUserRepository();
        FileMessageRepository fileMessageRepository = new FileMessageRepository();
        FileChannelRepository fileChannelRepository = new FileChannelRepository();

        BasicUserService fileBasicUserService = new BasicUserService(fileUserRepository);
        BasicChannelService fileBasicChannelService = new BasicChannelService(fileChannelRepository);
        BasicMessageService fileBasicMessageService = new BasicMessageService(fileMessageRepository, fileChannelRepository);


        User FileUser = setupUser(fileBasicUserService);
        Channel FileChannel = setupChannel(fileBasicChannelService,FileUser);
        // 테스트
        messageCreateTest(fileBasicMessageService, FileChannel, FileUser);







    }

}
