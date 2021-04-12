package com.springboot.pjt1.repository.jpa.dto;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {
    private static final long serialVersionUiD = 1L;

    public SecurityMember(Member member){
        super(member.getMemberId(),"{noop}"+ member.getMemberPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }

}
