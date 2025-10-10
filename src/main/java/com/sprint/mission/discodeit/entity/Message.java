package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseUpdatableEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = 3L;

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "message")
    private List<BinaryContent> attachments;

    @Column(nullable = false)
    private String content; // 내용. 수정 가능.

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;


//    public Message(UUID userId, UUID channelId, String content){
//        this.id = UUID.randomUUID();
//        this.userId = userId;
//        this.channelId = channelId;
//        this.content = content;
//    }

//    public void setBinaryContents(List<UUID> binaryContents) { // 메시지 첨부파일 수정
//        this.binaryContents = (binaryContents != null ? binaryContents : new ArrayList<>());
//    }
}
