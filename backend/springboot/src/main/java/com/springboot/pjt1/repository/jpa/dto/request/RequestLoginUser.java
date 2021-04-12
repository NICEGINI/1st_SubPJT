package com.springboot.pjt1.repository.jpa.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLoginUser {
    private String memberId;
    private String memberPassword;

    public RequestLoginUser(String memberId, String memberPassword) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
    }
}
