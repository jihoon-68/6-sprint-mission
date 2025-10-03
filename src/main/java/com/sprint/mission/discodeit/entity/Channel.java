package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Channel extends BaseUpdatableEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = 2L;

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ChannelType type;

    @OneToMany(mappedBy = "message_id")
    private List<Message> messages;

    private String name;

    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

//    public Channel(ChannelType type, String name, String description) {
//        this.type = type;
//        this.name = name;
//        this.description = description;
//    }
}
