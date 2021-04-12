package com.springboot.pjt1.member.service;

import com.springboot.pjt1.config.UserRole;
import com.springboot.pjt1.repository.jpa.dto.Member;

import javassist.NotFoundException;

public interface AuthService {

    final String REDIS_CHANGE_PASSWORD_PREFIX="CPW";

    void signUpUser(Member member);

    Member loginUser(String id, String password) throws Exception;

    Member findByMemberId(String memberId) throws NotFoundException;
    Member socialFindByMemberId(String memberId);

    void verifyEmail(String key) throws NotFoundException;

    void sendVerificationMail(Member member) throws NotFoundException;

    void modifyUserRole(Member member, UserRole userRole);

    boolean isPasswordUuidValidate(String key);

    void changePassword(Member member, String password) throws NotFoundException;

    void requestChangePassword(Member member) throws NotFoundException;
    
    long size();
}
