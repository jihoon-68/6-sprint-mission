package com.sprint.mission.discodeit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.request.MessageRequestDTO;
import com.sprint.mission.discodeit.dto.api.request.MessageRequestDTO.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.api.response.MessageResponseDTO;
import com.sprint.mission.discodeit.exception.message.NoSuchMessageException;
import com.sprint.mission.discodeit.mapper.api.BinaryContentApiMapper;
import com.sprint.mission.discodeit.mapper.api.MessageApiMapper;
import com.sprint.mission.discodeit.mapper.api.UserApiMapper;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private MessageService messageService;
  @MockitoBean
  private MessageApiMapper messageApiMapper;
  @MockitoBean
  private BinaryContentApiMapper binaryContentApiMapper;
  @MockitoBean
  private UserApiMapper userApiMapper;

  private final UUID testMessageId = UUID.randomUUID();
  private final UUID testChannelId = UUID.randomUUID();
  private final UUID testUserId = UUID.randomUUID();
  private final String testContent = "Test message content";

  @Test
  @DisplayName("메시지 전송 - 성공")
  void sendMessage_Success() throws Exception {

    //given
    MessageRequestDTO.MessageCreateRequest request = MessageRequestDTO.MessageCreateRequest.builder()
        .content(testContent)
        .authorId(testUserId)
        .channelId(testChannelId)
        .build();

    MessageDTO.Message message = MessageDTO.Message.builder()
        .id(testMessageId)
        .content(testContent)
        .channelId(testChannelId)
        .build();

    MessageResponseDTO.FindMessageResponse response = MessageResponseDTO.FindMessageResponse.builder()
        .id(testMessageId)
        .content(testContent)
        .channelId(testChannelId)
        .build();

    when(messageService.createMessage(any(MessageDTO.CreateMessageCommand.class)))
        .thenReturn(message);
    when(messageApiMapper.toFindMessageResponse(any(MessageDTO.Message.class)))
        .thenReturn(response);

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .multipart("/api/messages")
            .file(new MockMultipartFile("messageCreateRequest",
                "",
                "application/json",
                objectMapper.writeValueAsBytes(request)))
            .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(testMessageId.toString()))
        .andExpect(jsonPath("$.content").value(testContent));

  }

  @Test
  @DisplayName("메시지 전송 - 실패 (필수 필드 누락)")
  void sendMessage_Fail_MissingRequiredField() throws Exception {

    //given
    MessageRequestDTO.MessageCreateRequest request = MessageRequestDTO.MessageCreateRequest.builder()
        .content(testContent)
        // .authorId(testUserId)
        .channelId(testChannelId)
        .build();

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .multipart("/api/messages")
            .file(new MockMultipartFile("messageCreateRequest",
                "",
                "application/json",
                objectMapper.writeValueAsBytes(request)))
            .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("메시지 수정 - 성공")
  void updateMessage_Success() throws Exception {

    //given
    MessageRequestDTO.MessageUpdateRequest request = MessageRequestDTO.MessageUpdateRequest.builder()
        .id(testMessageId)
        .content("Updated content")
        .build();
    MessageDTO.Message updatedMessage = MessageDTO.Message.builder()
        .id(testMessageId)
        .content("Updated content")
        .channelId(testChannelId)
        .build();

    MessageResponseDTO.FindMessageResponse response = MessageResponseDTO.FindMessageResponse.builder()
        .id(testMessageId)
        .content("Updated content")
        .channelId(testChannelId)
        .build();

    when(messageService.updateMessage(any(MessageDTO.UpdateMessageCommand.class)))
        .thenReturn(updatedMessage);
    when(messageApiMapper.toFindMessageResponse(any(MessageDTO.Message.class)))
        .thenReturn(response);

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .patch("/api/messages/{messageId}", testMessageId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").value("Updated content"));

  }

  @Test
  @DisplayName("메시지 수정 - 실패 (빈 내용)")
  void updateMessage_Fail_EmptyContent() throws Exception {

    //given
    MessageRequestDTO.MessageUpdateRequest request = MessageRequestDTO.MessageUpdateRequest.builder()
        .id(testMessageId)
        .content("")
        .build();

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .patch("/api/messages/{messageId}", testMessageId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("메시지 삭제 - 성공")
  void deleteMessage_Success() throws Exception {

    //given
    doNothing().when(messageService).deleteMessageById(testMessageId);

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/messages/{messageId}", testMessageId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("메시지 삭제 - 실패 (존재하지 않는 메시지)")
  void deleteMessage_Fail_MessageNotFound() throws Exception {

    //given
    doThrow(new NoSuchMessageException())
        .when(messageService).deleteMessageById(testMessageId);

    //when & then
    mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/messages/{messageId}", testMessageId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

  }

}