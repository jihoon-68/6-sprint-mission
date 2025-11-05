package com.sprint.mission.discodeit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sprint.mission.discodeit.controller.ChannelController;
import com.sprint.mission.discodeit.controller.MessageController;
import com.sprint.mission.discodeit.controller.UserController;
import com.sprint.mission.discodeit.dto.api.request.ChannelRequestDTO;
import com.sprint.mission.discodeit.dto.api.request.MessageRequestDTO;
import com.sprint.mission.discodeit.dto.api.request.PagingRequestDTO;
import com.sprint.mission.discodeit.dto.api.request.PagingRequestDTO.OffsetRequest;
import com.sprint.mission.discodeit.dto.api.request.UserRequestDTO;
import com.sprint.mission.discodeit.dto.api.response.ChannelResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.MessageResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.MessageResponseDTO.FindMessageResponse;
import com.sprint.mission.discodeit.dto.api.response.PagingResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.PagingResponseDTO.OffsetPageResponse;
import com.sprint.mission.discodeit.dto.api.response.UserResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.UserResponseDTO.FindUserResponse;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class ApiIntegrationTest {

  private RestClient restClient;
  private String baseUrl;
  private final UUID testUserId = UUID.randomUUID();
  private final UUID testChannelId = UUID.randomUUID();
  private final String testUsername = "tester";
  private final String testEmail = "test@example.com";
  private final String testPassword = "Testpass123@";
  private final String testDescription = "Test user description";

  @BeforeEach
  void setUp() {

    baseUrl = "http://localhost:" + 8080;
    restClient = RestClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build();

  }

  @Test
  @DisplayName("유저 API 테스트")
  void testUserApi() {

    //test user 생성
    //given
    UserRequestDTO.UserCreateRequest createUserRequest = new UserRequestDTO.UserCreateRequest(
        testUsername,
        testEmail,
        testPassword,
        testDescription
    );

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("userCreateRequest", createUserRequest);
    body.add("profile", null);

    //when
    UserResponseDTO.FindUserResponse response = restClient.post()
        .uri("/api/users")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body)
        .retrieve()
        .toEntity(UserResponseDTO.FindUserResponse.class).getBody();

    //then
    assertNotNull(response);
    assertEquals(testUsername, response.username());

    //when
    List<FindUserResponse> userList = restClient.get()
        .uri("/api/users")
        .retrieve().body(new ParameterizedTypeReference<List<FindUserResponse>>(){})
        .stream()
        .toList();

    //then
    assertNotNull(userList);
    assertEquals(testUsername, userList.get(0).username());

    //test user 수정
    //given
    UserRequestDTO.UserUpdateRequest updateRequest = UserRequestDTO.UserUpdateRequest.builder()
        .username("updatedUsername")
        .email("updated@example.com")
        .currentPassword(testPassword)
        .newPassword("Newpass123@")
        .build();

    body.clear();

    body.add("userUpdateRequest", updateRequest);
    body.add("profile", null);

    //when
    response = restClient.patch()
        .uri("/api/users/" + userList.get(0).id())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body)
        .retrieve()
        .toEntity(UserResponseDTO.FindUserResponse.class).getBody();

    //then
    assertNotNull(response);
    assertEquals("updatedUsername", response.username());

    //test user 삭제
    //when & then
    restClient.delete()
        .uri("/api/users/" + testUserId.toString());

  }

  @Test
  @DisplayName("채널 API 테스트")
  void testChannelApi() {

    //test 공개 채널 생성
    //given
    ChannelRequestDTO.PublicChannelCreateRequest publicChannelRequest =
        new ChannelRequestDTO.PublicChannelCreateRequest("테스트 공개 채널", "테스트용 공개 채널입니다.");

    //when
    ChannelResponseDTO.FindChannelResponse publicChannel = restClient.post()
        .uri("/api/channels/public")
        .contentType(MediaType.APPLICATION_JSON)
        .body(publicChannelRequest)
        .retrieve()
        .toEntity(ChannelResponseDTO.FindChannelResponse.class)
        .getBody();

    //then
    assertNotNull(publicChannel);
    assertEquals("테스트 공개 채널", publicChannel.name());

    //test 비공개 채널 생성
    //given
    UserRequestDTO.UserCreateRequest user1 = new UserRequestDTO.UserCreateRequest(
        "testUser1", "test1@example.com", "Password123!", "Test User 1");
    UserRequestDTO.UserCreateRequest user2 = new UserRequestDTO.UserCreateRequest(
        "testUser2", "test2@example.com", "Password123!", "Test User 2");

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("userCreateRequest", user1);
    body.add("profile", null);

    UserResponseDTO.FindUserResponse createdUser1 = restClient.post()
        .uri("/api/users")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body)
        .retrieve()
        .toEntity(UserResponseDTO.FindUserResponse.class)
        .getBody();

    body.clear();
    body.add("userCreateRequest", user2);
    body.add("profile", null);

    UserResponseDTO.FindUserResponse createdUser2 = restClient.post()
        .uri("/api/users")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(body)
        .retrieve()
        .toEntity(UserResponseDTO.FindUserResponse.class)
        .getBody();

    ChannelRequestDTO.PrivateChannelCreateRequest privateChannelRequest =
        new ChannelRequestDTO.PrivateChannelCreateRequest(
            List.of(createdUser1.id(), createdUser2.id())
        );

    //when
    ChannelResponseDTO.FindChannelResponse privateChannel = restClient.post()
        .uri("/api/channels/private")
        .contentType(MediaType.APPLICATION_JSON)
        .body(privateChannelRequest)
        .retrieve()
        .toEntity(ChannelResponseDTO.FindChannelResponse.class)
        .getBody();

    //then
    assertNotNull(privateChannel);
    assertEquals(2, privateChannel.participants().size());

    //test 공개 채널 수정
    //given
    ChannelRequestDTO.ChannelUpdateRequest updateRequest = ChannelRequestDTO.ChannelUpdateRequest.builder()
        .name("수정된 채널명")
        .description("수정된 채널 설명")
        .build();

    //when
    ChannelResponseDTO.FindChannelResponse updatedChannel = restClient.patch()
        .uri("/api/channels/" + publicChannel.id())
        .contentType(MediaType.APPLICATION_JSON)
        .body(updateRequest)
        .retrieve()
        .toEntity(ChannelResponseDTO.FindChannelResponse.class)
        .getBody();

    //then
    assertNotNull(updatedChannel);
    assertEquals("수정된 채널명", updatedChannel.name());

    //test 공개 채널 삭제
    //when & then
    restClient.delete()
        .uri("/api/channels/" + publicChannel.id());

  }

  @Test
  @DisplayName("메시지 API 테스트")
  void testMessageApi() {

    //test 메시지 전송
    //given
    UserRequestDTO.UserCreateRequest userRequest = new UserRequestDTO.UserCreateRequest(
        "messageTester", "message@example.com", "Password123!", "Message Test User");

    MultiValueMap<String, Object> userBody = new LinkedMultiValueMap<>();
    userBody.add("userCreateRequest", userRequest);
    userBody.add("profile", null);

    UserResponseDTO.FindUserResponse testUser = restClient.post()
        .uri("/api/users")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(userBody)
        .retrieve()
        .toEntity(UserResponseDTO.FindUserResponse.class)
        .getBody();

    ChannelRequestDTO.PublicChannelCreateRequest channelRequest =
        new ChannelRequestDTO.PublicChannelCreateRequest("메시지 테스트 채널", "메시지 API 테스트용 채널입니다.");

    ChannelResponseDTO.FindChannelResponse testChannel = restClient.post()
        .uri("/api/channels/public")
        .contentType(MediaType.APPLICATION_JSON)
        .body(channelRequest)
        .retrieve()
        .toEntity(ChannelResponseDTO.FindChannelResponse.class)
        .getBody();

    MessageRequestDTO.MessageCreateRequest textMessageRequest =
        new MessageRequestDTO.MessageCreateRequest("안녕하세요! 테스트 메시지입니다.", testUser.id(), testChannel.id());

    MultiValueMap<String, Object> messageBody = new LinkedMultiValueMap<>();
    messageBody.add("messageCreateRequest", textMessageRequest);
    messageBody.add("attachments", null);

    //when
    MessageResponseDTO.FindMessageResponse sentMessage = restClient.post()
        .uri("/api/messages")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(messageBody)
        .retrieve()
        .toEntity(MessageResponseDTO.FindMessageResponse.class)
        .getBody();

    //then
    assertNotNull(sentMessage);
    assertEquals("안녕하세요! 테스트 메시지입니다.", sentMessage.content());

    //test 메시지 수정
    //given
    MessageRequestDTO.MessageUpdateRequest updateRequest = MessageRequestDTO.MessageUpdateRequest.builder()
        .id(sentMessage.id())
        .content("수정된 메시지 내용입니다.")
        .build();

    //when
    MessageResponseDTO.FindMessageResponse updatedMessage = restClient.patch()
        .uri("/api/messages/" + sentMessage.id())
        .contentType(MediaType.APPLICATION_JSON)
        .body(updateRequest)
        .retrieve()
        .toEntity(MessageResponseDTO.FindMessageResponse.class)
        .getBody();

    //then
    assertNotNull(updatedMessage);
    assertEquals("수정된 메시지 내용입니다.", updatedMessage.content());

    //test channel 별 메시지 조회
    //given
    PagingRequestDTO.OffsetRequest offsetRequest = OffsetRequest.builder()
        .page(0)
        .size(10)
        .sort("createdAt,DESC")
        .build();

    //when
    OffsetPageResponse<FindMessageResponse>messageResponseOffsetPageResponse = restClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/messages/offset")
            .queryParam("channelId", testChannel.id())
            .queryParam("page", offsetRequest.page())
            .queryParam("size", offsetRequest.size())
            .queryParam("sort", offsetRequest.sort())
            .build())
        .retrieve()
        .toEntity(new ParameterizedTypeReference<PagingResponseDTO.OffsetPageResponse<FindMessageResponse>>() {})
        .getBody();

    //then
    assertNotNull(messageResponseOffsetPageResponse);
    assertEquals(1, messageResponseOffsetPageResponse.content().size());
    assertEquals(sentMessage.id(), messageResponseOffsetPageResponse.content().get(0).id());

    //test 메시지 삭제
    //when & then
    restClient.delete()
        .uri("/api/messages/" + sentMessage.id());

  }

}
