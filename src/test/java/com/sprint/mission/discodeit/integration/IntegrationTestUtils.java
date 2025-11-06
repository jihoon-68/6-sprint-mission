package com.sprint.mission.discodeit.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

final class IntegrationTestUtils {

  private IntegrationTestUtils() {
  }

  static UserDto createUser(MockMvc mockMvc, ObjectMapper objectMapper,
      String username, String email, String password) throws Exception {
    UserCreateRequest request = new UserCreateRequest(username, email, password);
    MockMultipartFile requestPart = new MockMultipartFile(
        "userCreateRequest",
        "",
        MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(request)
    );

    MockHttpServletRequestBuilder builder = multipart("/api/users")
        .file(requestPart)
        .characterEncoding(StandardCharsets.UTF_8)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(builder)
        .andExpect(status().isCreated())
        .andReturn();

    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);
  }

  static ChannelDto createPublicChannel(MockMvc mockMvc, ObjectMapper objectMapper,
      String name, String description) throws Exception {
    PublicChannelCreateRequest request = new PublicChannelCreateRequest(name, description);

    MvcResult mvcResult = mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                    "/api/channels/public")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ChannelDto.class);
  }

  static ChannelDto createPrivateChannel(MockMvc mockMvc, ObjectMapper objectMapper,
      List<UUID> participantIds) throws Exception {
    PrivateChannelCreateRequest request = new PrivateChannelCreateRequest(participantIds);

    MvcResult mvcResult = mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(
                    "/api/channels/private")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ChannelDto.class);
  }
}
