package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.repository.jcf.*;
import com.sprint.mission.discodeit.service.*;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class JavaApplication {
    public static void main(String[] args) {

        // 의존성 선택

        UserRepository userRepository = new JCFUserRepository();
        ChannelRepository channelRepository = new JCFChannelRepository();
        MessageRepository messageRepository = new JCFMessageRepository();
        ReadStatusRepository readStatusRepository = new JCFReadStatusRepository();
        UserStatusRepository userStatusRepository = new JCFUserStatusRepository();
        BinaryContentRepository binaryContentRepository = new JCFBinaryContentRepository();

//        UserRepository userRepository = new FileUserRepository();
//        ChannelRepository channelRepository = new FileChannelRepository();
//        MessageRepository messageRepository = new FileMessageRepository();
//        ReadStatusRepository readStatusRepository = new FileReadStatusRepository();
//        UserStatusRepository userStatusRepository = new FileUserStatusRepository();
//        BinaryContentRepository binaryContentRepository = new FileBinaryContentRepository();

        UserService userService = new UserService(userRepository, userStatusRepository, binaryContentRepository);
        ChannelService channelService = new ChannelService(channelRepository, messageRepository, readStatusRepository);
        MessageService messageService = new MessageService(userRepository, channelRepository, messageRepository, binaryContentRepository);
        UserStatusService userStatusService = new UserStatusService(userStatusRepository, userRepository);
        ReadStatusService readStatusService = new ReadStatusService(readStatusRepository, userRepository, channelRepository);
        BinaryContentService binaryContentService = new BinaryContentService(binaryContentRepository);

        // file test 시 clear 필요

        userService.clear();
        channelService.clear();
        messageService.clear();

        test(userService, channelService, messageService, readStatusService, userStatusService, binaryContentService);
    }


    public static void test(UserService userService,
                            ChannelService channelService,
                            MessageService messageService,
                            ReadStatusService readStatusService,
                            UserStatusService userStatusService,
                            BinaryContentService binaryContentService){

        Scanner sc = new Scanner(System.in);
        boolean status = true;
        int num;

        while (status) {
            System.out.println("테스트할 서비스를 선택해주세요.");
            System.out.println("1. User, 2. Channel, 3. Message");
            System.out.println("4. ReadStatus, 5. UserStatus, 6. BinaryContent");
            System.out.println("7. 프로그램 종료");
            System.out.print("선택: ");
            num = sc.nextInt();
            sc.nextLine();

            switch (num){
                case 1:
                    // 유저 생성
                    UserCreateRequestDto userCreateRequestDto1 = new UserCreateRequestDto("ex@ex.com", "갈매기", "1234", null);
                    UserCreateRequestDto userCreateRequestDto2 = new UserCreateRequestDto("ex@ex2.com", "참새", "5678", null);
                    UserResponseDto userResponseDto1 = userService.create(userCreateRequestDto1);
                    UserResponseDto userResponseDto2 = userService.create(userCreateRequestDto2);

                    // 조회 단건
                    System.out.print("유저 단건 조회 : ");
                    System.out.println(userService.findByUsername("참새"));

                    // 조회 다건
                    System.out.println("모든 유저 조회 : ");
                    List<UserResponseDto> users = userService.findAll();
                    System.out.println(users);

                    // 정보 수정 및 수정 확인 - 바꾸기 싫은건 null로
                    UserUpdateRequestDto updateRequestDto = new UserUpdateRequestDto(userResponseDto1.id(), null, null, "참새2");
                    userService.update(updateRequestDto);
                    System.out.println("수정 확인 :");
                    System.out.println(userService.findByUsername("참새").toString());

                    // 삭제 및 삭제 확인
                    userService.delete(userResponseDto2.id());
                    System.out.println("삭제 확인 :");
                    System.out.println(userService.findAll());
                    break;

                case 2:
                    // 등록
                    UserCreateRequestDto userCreateRequestDto3 = new UserCreateRequestDto("ex@ex3.com", "까치", "0000", null);
                    UserResponseDto userResponseDto3 = userService.create(userCreateRequestDto3);

                    PrivateChannelCreateRequestDto channelCreateRequestDto1
                            = new PrivateChannelCreateRequestDto(userResponseDto3.id(), null);
                    ChannelResponseDto channelResponseDto1 = channelService.createPrivateChannel(channelCreateRequestDto1);

                    PublicChannelCreateRequestDto channelCreateRequestDto2
                            = new PublicChannelCreateRequestDto(userResponseDto3.id(), "잡담방", "잡담방입니다.");
                    ChannelResponseDto channelResponseDto2 = channelService.createPrivateChannel(channelCreateRequestDto1);

                    // 조회 단건
                    System.out.println("채널 단건 조회");
                    System.out.println(channelService.findById(channelResponseDto1.id()).toString());

                    // 조회 다건
                    System.out.println("모든 채널 조회 : ");
                    List<ChannelResponseDto> channels = channelService.findAllByUserId(userResponseDto3.id());
                    System.out.println(channels);

                    // 정보 수정 및 확인
                    ChannelUpdateRequestDto channelUpdateRequestDto = new ChannelUpdateRequestDto(channelResponseDto2.id(), "잡담잡담방", null);
                    channelService.update(channelUpdateRequestDto);
                    System.out.println("수정 확인 :");
                    System.out.println(channelService.findById(channelResponseDto2.id()).toString());

                    // 삭제 및 확인
                    channelService.deleteById(channelResponseDto2.id());

                    System.out.println("삭제 확인하기 :");
                    System.out.println(channelService.findById(channelResponseDto2.id())); // null 반환

                    break;

                case 3:
                    // 유저, 채널, 메시지 생성
                    UserCreateRequestDto userCreateRequestDto4
                            = new UserCreateRequestDto("ex@ex4.com", "갈매기", "0000", null);
                    UserResponseDto userResponseDto4 = userService.create(userCreateRequestDto4);

                    PublicChannelCreateRequestDto channelCreateRequestDto3
                            = new PublicChannelCreateRequestDto(userResponseDto4.id(), "아무거나방", "아무거나방입니다.");
                    ChannelResponseDto channelResponseDto3 = channelService.createPublicChannel(channelCreateRequestDto3);

                    MessageCreateRequestDto messageCreateRequestDto1
                            = new MessageCreateRequestDto(userResponseDto4.id(), channelResponseDto3.id(), "안녕하세요", null);
                    MessageResponseDto messageResponseDto1 = messageService.create(messageCreateRequestDto1);

                    // 메시지 단건 조회
                    System.out.println(messageService.findById(messageResponseDto1.id()));

                    // 채널별 메시지 조회
                    List<MessageResponseDto> messageResponseDtos1 = messageService.findByChannelId(channelResponseDto3.id());
                    System.out.println(messageResponseDtos1);

                    // 메시지 수정 및 확인
                    MessageUpdateRequestDto messageUpdateRequestDto = new MessageUpdateRequestDto(messageResponseDto1.id(), "안녕안녕하세요");
                    messageService.update(messageUpdateRequestDto);
                    System.out.println();

                    // 삭제 및 확인
                    messageService.delete(messageResponseDto1.id());
                    List<MessageResponseDto> messageResponseDtos2 = messageService.findByChannelId(channelResponseDto3.id());
                    System.out.println(messageResponseDtos2);
                    break;

                case 4: // ReadStatus
                    // 유저, 채널, 메시지 생성
                    UserCreateRequestDto readUserDto = new UserCreateRequestDto("read@ex.com", "독수리", "1111", null);
                    UserResponseDto readUser = userService.create(readUserDto);

                    PublicChannelCreateRequestDto readChannelDto = new PublicChannelCreateRequestDto(readUser.id(), "읽음방", "읽음 테스트 방");
                    ChannelResponseDto readChannel = channelService.createPublicChannel(readChannelDto);

                    MessageCreateRequestDto readMessageDto = new MessageCreateRequestDto(readUser.id(), readChannel.id(), "읽음 확인 메시지", null);
                    MessageResponseDto readMessage = messageService.create(readMessageDto);

                    // 읽음 상태 생성
                    ReadStatusCreateRequestDto rsCreateDto = new ReadStatusCreateRequestDto(readUser.id(), readChannel.id());
                    ReadStatusResponseDto rsResponseDto = readStatusService.create(rsCreateDto);

                    // 조회
                    System.out.println("유저별 ReadStatus 조회:");
                    System.out.println(readStatusService.findAllByUserId(readUser.id()));

                    // 삭제 및 확인
                    readStatusService.deleteById(rsResponseDto.id());
                    System.out.println("삭제 후 조회:");
                    System.out.println(readStatusService.findAllByUserId(readUser.id()));
                    break;

                case 5: // UserStatus
                    // 유저 생성
                    UserCreateRequestDto statusUserDto = new UserCreateRequestDto("status@ex.com", "비둘기", "2222", null);
                    UserResponseDto usUser = userService.create(statusUserDto);

                    // 상태 생성
                    UserStatusCreateRequestDto usCreateDto
                            = new UserStatusCreateRequestDto(usUser.id(), null);
                    UserStatusResponseDto usResponseDto
                            = userStatusService.create(usCreateDto);

                    // 조회
                    System.out.println("UserStatus 조회:");
                    System.out.println(userStatusService.findById(usResponseDto.id()));

                    // 업데이트
                    userStatusService.updateByUserId(usUser.id(), Instant.now());
                    System.out.println("업데이트 후 UserStatus:");
                    System.out.println(userStatusService.findById(usResponseDto.id()));

                    // 삭제
                    userStatusService.deleteById(usUser.id());
                    System.out.println("삭제 후 UserStatus:");
                    System.out.println(userStatusService.findById(usResponseDto.id()));
                    break;

                case 6: // BinaryContent
                    // 유저 생성
                    UserCreateRequestDto bcUserDto
                            = new UserCreateRequestDto("binary@ex.com", "까마귀", "3333", null);
                    UserResponseDto bcUser = userService.create(bcUserDto);

                    // BinaryContent 생성
                    byte[] data = "example".getBytes();
                    BinaryContentCreateRequestDto bcRequestDto
                            = new BinaryContentCreateRequestDto(bcUser.id(), null, BinaryContentType.PROFILE_IMAGE, data);
                    BinaryContentResponseDto bcResponseDto = binaryContentService.create(bcRequestDto);

                    // 조회
                    System.out.println("BinaryContent 단건 조회:");
                    System.out.println(binaryContentService.findById(bcResponseDto.id()));

                    // 삭제 및 확인
                    binaryContentService.deleteById(bcResponseDto.id());
                    System.out.println("삭제 후 조회:");
                    System.out.println(binaryContentService.findById(bcResponseDto.id()));

                case 7:
                    System.out.println("종료합니다.");
                    status = false;
                    break;

                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
        sc.close();
    }
}
