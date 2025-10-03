package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

// 프사 또는 첨부파일

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinaryContent extends BaseEntity implements Serializable{

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "binary_content_id")
    private User user;

    @Column(nullable = false, updatable = false)
    private BinaryContentType type;

    @Column(nullable = false, updatable = false)
    private String fileName;

    @Column(nullable = false, updatable = false)
    private String extension;

    @Column(nullable = false, updatable = false)
    private Long size;

    @Column(nullable = false, updatable = false)
    private byte[] data;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

//    public BinaryContent(String name, String extension, BinaryContentType type, byte[] data, Long size) {
//        this.fileName = name;
//        this.extension = extension;
//        this.type = type;
//        this.data = data;
//        this.size = size;
//    }
}

