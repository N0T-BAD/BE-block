package com.blockpage.blockservice.adaptor.infrastructure.mysql.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate
    @Column(name = "register_time", nullable = false, updatable = false)
    private LocalDateTime registerTime;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
