package com.sprint.mission.discodeit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User API 통합 테스트")
class UserApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @Transactional
  @DisplayName("사용자 생성 - 201 Created")
  void createUser_success() throws Exception {
    UserDto created = createUser("integration-user-create", "integration-user-create@example.com");

    assertThat(created.username()).isEqualTo("integration-user-create");
    assertThat(created.email()).isEqualTo("integration-user-create@example.com");
  }

  @Test
  @Transactional
  @DisplayName("사용자 수정 - 200 OK")
  void updateUser_success() throws Exception {
    UserDto existing =
        createUser("integration-user-update", "integration-user-update@example.com");

    UserUpdateRequest updateRequest = new UserUpdateRequest(
        "integration-user-update-new",
        "integration-user-update-new@example.com",
        "new-password"
    );
    MockMultipartFile requestPart = jsonPart("userUpdateRequest", updateRequest);
    MockHttpServletRequestBuilder builder = multipart("/api/users/{userId}", existing.id())
        .file(requestPart)
        .with(request -> {
          request.setMethod("PATCH");
          return request;
        })
        .characterEncoding(StandardCharsets.UTF_8)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andReturn();

    UserDto updated =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
    assertThat(updated.username()).isEqualTo("integration-user-update-new");
    assertThat(updated.email()).isEqualTo("integration-user-update-new@example.com");
  }

  @Test
  @Transactional
  @DisplayName("사용자 삭제 - 204 No Content")
  void deleteUser_success() throws Exception {
    UserDto existing =
        createUser("integration-user-delete", "integration-user-delete@example.com");

    mockMvc.perform(delete("/api/users/{userId}", existing.id()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  @DisplayName("사용자 목록 조회 - 200 OK")
  void findAllUsers_success() throws Exception {
    createUser("integration-user-list-1", "integration-user-list-1@example.com");
    createUser("integration-user-list-2", "integration-user-list-2@example.com");

    MvcResult mvcResult = mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andReturn();

    List<UserDto> users = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(),
        new TypeReference<>() {});
    assertThat(users).extracting(UserDto::username)
        .contains("integration-user-list-1", "integration-user-list-2");
  }

  private UserDto createUser(String username, String email) throws Exception {
    return IntegrationTestUtils.createUser(
        mockMvc,
        objectMapper,
        username,
        email,
        "test-password"
    );
  }

  private MockMultipartFile jsonPart(String name, Object value) throws Exception {
    return new MockMultipartFile(
        name,
        "",
        MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(value)
    );
  }
}
