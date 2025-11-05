package com.sprint.mission.discodeit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sprint.mission.discodeit.controller.ChannelController;
import com.sprint.mission.discodeit.controller.MessageController;
import com.sprint.mission.discodeit.controller.UserController;
import com.sprint.mission.discodeit.dto.api.request.UserRequestDTO;
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
  void testCreateAndGetUser() {

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

    //when & then
    restClient.delete()
        .uri("/api/users/" + testUserId.toString());

  }

}
