package com.sprint.mission.discodeit.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Channel API 통합 테스트")
class ChannelApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @Transactional
  @DisplayName("Public 채널 생성 - 201 Created")
  void createPublicChannel_success() throws Exception {
    ChannelDto channel = IntegrationTestUtils.createPublicChannel(
        mockMvc,
        objectMapper,
        "integration-channel-create",
        "desc"
    );

    assertThat(channel.name()).isEqualTo("integration-channel-create");
  }

  @Test
  @Transactional
  @DisplayName("Public 채널 수정 - 200 OK")
  void updateChannel_success() throws Exception {
    ChannelDto existing = IntegrationTestUtils.createPublicChannel(
        mockMvc,
        objectMapper,
        "integration-channel-update",
        "desc"
    );

    PublicChannelUpdateRequest request =
        new PublicChannelUpdateRequest("integration-channel-update-new", "new desc");

    MvcResult mvcResult = mockMvc.perform(patch("/api/channels/{channelId}", existing.id())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isOk())
        .andReturn();

    ChannelDto updated =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ChannelDto.class);
    assertThat(updated.name()).isEqualTo("integration-channel-update-new");
  }

  @Test
  @Transactional
  @DisplayName("Private 채널 수정 시 403 Forbidden")
  void updateChannel_failure_private() throws Exception {
    UserDto participant = IntegrationTestUtils.createUser(
        mockMvc,
        objectMapper,
        "integration-channel-private-user",
        "integration-channel-private-user@example.com",
        "password"
    );

    ChannelDto privateChannel = IntegrationTestUtils.createPrivateChannel(
        mockMvc,
        objectMapper,
        List.of(participant.id())
    );

    PublicChannelUpdateRequest request =
        new PublicChannelUpdateRequest("name", "desc");

    MvcResult mvcResult = mockMvc.perform(patch("/api/channels/{channelId}", privateChannel.id())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isForbidden())
        .andReturn();

    Map<String, Object> errorBody = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(errorBody.get("code")).isEqualTo(ErrorCode.UPDATE_PRIVATE_CHANNEL.getCode());
  }

  @Test
  @Transactional
  @DisplayName("채널 삭제 - 204 No Content")
  void deleteChannel_success() throws Exception {
    ChannelDto existing = IntegrationTestUtils.createPublicChannel(
        mockMvc,
        objectMapper,
        "integration-channel-delete",
        "desc"
    );

    mockMvc.perform(delete("/api/channels/{channelId}", existing.id()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  @DisplayName("사용자 채널 목록 조회 - 200 OK")
  void findChannels_success() throws Exception {
    ChannelDto publicChannel = IntegrationTestUtils.createPublicChannel(
        mockMvc,
        objectMapper,
        "integration-channel-list",
        "desc"
    );

    MvcResult mvcResult = mockMvc.perform(get("/api/channels")
            .param("userId", UUID.randomUUID().toString()))
        .andExpect(status().isOk())
        .andReturn();

    List<ChannelDto> channels = objectMapper.readValue(
        mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(channels).extracting(ChannelDto::id).contains(publicChannel.id());
  }
}
