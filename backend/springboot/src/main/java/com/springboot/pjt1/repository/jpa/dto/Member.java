package com.springboot.pjt1.repository.jpa.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.springboot.pjt1.config.UserRole;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private int seq;

    @Column(unique = true)
    @NotNull
    private String memberId;
    @NotNull
    private String memberPassword;
    @NotBlank
    private String memberName;
    private String provider;
    @Column(columnDefinition = "int default 0")
    private int blind;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_NOT_PERMITTED;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private Salt salt;

    public Member() {
    }

    public Member(@NotBlank String memberId, @NotBlank String memberPassword, @NotBlank String memberName) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq=" + seq +
                ", id='" + memberId + '\'' +
                ", password='" + memberPassword + '\'' +
                ", name='" + memberName + '\'' +
                ", role=" + role +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
