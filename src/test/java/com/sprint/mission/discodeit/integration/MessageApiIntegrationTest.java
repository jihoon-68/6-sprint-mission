package com.sprint.mission.discodeit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Map;
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
@DisplayName("Message API 통합 테스트")
class MessageApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @Transactional
  @DisplayName("메시지 생성 - 201 Created")
  void createMessage_success() throws Exception {
    TestContext context = prepareContext();

    MessageDto message = createMessage(context.channel().id(), context.author().id(), "hello");

    assertThat(message.content()).isEqualTo("hello");
    assertThat(message.channelId()).isEqualTo(context.channel().id());
  }

  @Test
  @Transactional
  @DisplayName("메시지 수정 - 200 OK")
  void updateMessage_success() throws Exception {
    TestContext context = prepareContext();
    MessageDto existing = createMessage(context.channel().id(), context.author().id(), "before");

    MessageUpdateRequest request = new MessageUpdateRequest("after");
    MvcResult mvcResult = mockMvc.perform(patch("/api/messages/{messageId}", existing.id())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isOk())
        .andReturn();

    MessageDto updated = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(), MessageDto.class);
    assertThat(updated.content()).isEqualTo("after");
  }

  @Test
  @Transactional
  @DisplayName("메시지 삭제 - 204 No Content")
  void deleteMessage_success() throws Exception {
    TestContext context = prepareContext();
    MessageDto existing = createMessage(context.channel().id(), context.author().id(), "to delete");

    mockMvc.perform(delete("/api/messages/{messageId}", existing.id()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  @DisplayName("채널 메시지 목록 조회 - 200 OK")
  void findMessages_success() throws Exception {
    TestContext context = prepareContext();
    createMessage(context.channel().id(), context.author().id(), "first");
    createMessage(context.channel().id(), context.author().id(), "second");

    MvcResult mvcResult = mockMvc.perform(get("/api/messages")
            .param("channelId", context.channel().id().toString()))
        .andExpect(status().isOk())
        .andReturn();

    Map<String, Object> body = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> content = (List<Map<String, Object>>) body.get("content");
    assertThat(content).hasSizeGreaterThanOrEqualTo(2);
  }

  @Test
  @Transactional
  @DisplayName("존재하지 않는 메시지 수정 시 404 Not Found")
  void updateMessage_failure_notFound() throws Exception {
    MessageUpdateRequest request = new MessageUpdateRequest("irrelevant");

    MvcResult mvcResult = mockMvc.perform(patch("/api/messages/{messageId}", UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isNotFound())
        .andReturn();

    Map<String, Object> body = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(body.get("code")).isEqualTo(ErrorCode.MESSAGE_NOT_FOUND.getCode());
  }

  private MessageDto createMessage(UUID channelId, UUID authorId, String content) throws Exception {
    MessageCreateRequest request = new MessageCreateRequest(content, channelId, authorId);
    MockMultipartFile requestPart = new MockMultipartFile(
        "messageCreateRequest",
        "",
        MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(request)
    );

    MockHttpServletRequestBuilder builder = multipart("/api/messages")
        .file(requestPart)
        .characterEncoding(StandardCharsets.UTF_8)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(builder)
        .andExpect(status().isCreated())
        .andReturn();

    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageDto.class);
  }

  private TestContext prepareContext() throws Exception {
    String userToken = UUID.randomUUID().toString().substring(0, 8);
    String channelToken = UUID.randomUUID().toString().substring(0, 8);
    UserDto author = IntegrationTestUtils.createUser(
        mockMvc,
        objectMapper,
        "msg-user-" + userToken,
        "msg-user-" + userToken + "@example.com",
        "password"
    );
    ChannelDto channel = IntegrationTestUtils.createPublicChannel(
        mockMvc,
        objectMapper,
        "msg-channel-" + channelToken,
        "desc"
    );
    return new TestContext(author, channel);
  }

  private record TestContext(UserDto author, ChannelDto channel) {
  }
}
