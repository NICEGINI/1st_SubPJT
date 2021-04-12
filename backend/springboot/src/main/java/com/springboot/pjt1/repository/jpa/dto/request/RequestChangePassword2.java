package com.springboot.pjt1.repository.jpa.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestChangePassword2 {
    String memberId;
    String memberPassword;
}
