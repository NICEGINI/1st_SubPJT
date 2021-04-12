package com.springboot.pjt1.repository.jpa.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RequestVerifyEmail {
    String memberId;
}
