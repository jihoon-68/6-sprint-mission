package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseUpdatableEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = 1L;

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

//    @OneToMany(mappedBy = "")
//    private List<Channel> channels; // 속해있는 채널

    @OneToMany(mappedBy = "author")
    private List<Message> messages; // 작성한 메시지

    @OneToMany(mappedBy = "user")
    private List<ReadStatus> readStatuses;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserStatus userStatus;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private BinaryContent profile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private transient String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
