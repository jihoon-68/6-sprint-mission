package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BaseEntity;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.InstantSource;
import java.util.UUID;
import java.util.function.Supplier;

@Aspect
@Component
class BaseEntityAspect {

    private static final Logger log = LoggerFactory.getLogger(BaseEntityAspect.class);

    private final Supplier<UUID> idGenerator;
    private final InstantSource instantSource;

    BaseEntityAspect(Supplier<UUID> idGenerator, InstantSource instantSource) {
        this.idGenerator = idGenerator;
        this.instantSource = instantSource;
    }

    @Before("execution(* CrudRepository+.save(..)) && args(baseEntity)")
    void save(BaseEntity baseEntity) {
        Instant now = instantSource.instant();
        if (baseEntity.isTransient()) {
            baseEntity.setId(idGenerator.get());
            baseEntity.setCreatedAt(now);
        } else if (baseEntity instanceof BinaryContent) {
            return;
        }
        baseEntity.setUpdatedAt(now);
        log.debug("New {}: {}", baseEntity.getClass().getSimpleName(), baseEntity);
    }
}
