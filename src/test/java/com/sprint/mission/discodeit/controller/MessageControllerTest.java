package com.sprint.mission.discodeit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.GlobalExceptionHandler;
import com.sprint.mission.discodeit.exception.MessageNotFoundException;
import com.sprint.mission.discodeit.service.MessageService;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MessageController.class)
@Import(GlobalExceptionHandler.class)
@TestPropertySource(properties = "spring.test.context.bean.override.mode=override")
@DisplayName("MessageController")
class MessageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private MessageService messageService;

  @Test
  @DisplayName("GET /api/messages - 채널 메시지 페이지 반환")
  void findAllByChannel_success() throws Exception {
    UUID channelId = UUID.randomUUID();
    Instant cursor = Instant.now();
    MessageDto messageDto = new MessageDto(
        UUID.randomUUID(),
        cursor.minusSeconds(30),
        cursor.minusSeconds(10),
        "hello",
        channelId,
        new UserDto(UUID.randomUUID(), "john", "john@example.com", null, true),
        List.of(new BinaryContentDto(UUID.randomUUID(), "file.txt", 12L, "text/plain"))
    );
    PageResponse<MessageDto> response =
        new PageResponse<>(List.of(messageDto), cursor, 50, false, 1L);
    given(messageService.findAllByChannelId(eq(channelId), any(Instant.class),
        any(org.springframework.data.domain.Pageable.class)))
        .willReturn(response);

    mockMvc.perform(get("/api/messages")
            .param("channelId", channelId.toString())
            .param("cursor", cursor.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].content").value("hello"))
        .andExpect(jsonPath("$.nextCursor").value(cursor.toString()))
        .andExpect(jsonPath("$.size").value(50));
  }

  @Test
  @DisplayName("PATCH /api/messages/{id} - 메시지를 찾지 못하면 404 반환")
  void update_failure_messageNotFound() throws Exception {
    UUID messageId = UUID.randomUUID();
    MessageUpdateRequest request = new MessageUpdateRequest("updated");
    willThrow(new MessageNotFoundException(Map.of("messageId", messageId)))
        .given(messageService).update(eq(messageId), any(MessageUpdateRequest.class));

    mockMvc.perform(patch("/api/messages/{messageId}", messageId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value(ErrorCode.MESSAGE_NOT_FOUND.getCode()));
  }
}
