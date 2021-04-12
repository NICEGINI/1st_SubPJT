package com.springboot.pjt1.member.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.config.UserRole;
import com.springboot.pjt1.repository.jpa.MemberRepository;
import com.springboot.pjt1.repository.jpa.dto.Member;
import com.springboot.pjt1.repository.jpa.dto.Salt;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SaltUtil saltUtil;

    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public long size() {
    	return memberRepository.count();
    }

    @Override
    @Transactional
    public void signUpUser(Member member) {
        String password = member.getMemberPassword();
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setMemberPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }

    @Override
    public Member loginUser(String id, String password) throws Exception{
        Member member = memberRepository.findByMemberId(id);
        if(member==null) throw new Exception ("멤버가 조회되지 않음");
        String salt = member.getSalt().getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getMemberPassword().equals(password))
            throw new Exception ("비밀번호가 틀립니다.");
        return member;
    }

    @Override
    public Member findByMemberId(String username) throws NotFoundException {
        Member member = memberRepository.findByMemberId(username);
        if(member == null) throw new NotFoundException("멤버가 조회되지 않음");
        return member;
    }

    @Override
    public Member socialFindByMemberId(String username) {
        Member member = memberRepository.findByMemberId(username);
        return member;
    }
    
    @Override
    public void verifyEmail(String key) throws NotFoundException {
        String memberId = redisUtil.getData(key);
        Member member = memberRepository.findByMemberId(memberId);
        if(member==null) throw new NotFoundException("멤버가 조회되지않음");
        modifyUserRole(member,UserRole.ROLE_USER);
        redisUtil.deleteData(key);
    }

    @Override
    public void sendVerificationMail(Member member) throws NotFoundException {
        String VERIFICATION_LINK = "http://localhost:8080/user/verify/";
        if(member==null) throw new NotFoundException("멤버가 조회되지 않음");
        UUID uuid = UUID.randomUUID();
        redisUtil.setDataExpire(uuid.toString(),member.getMemberId(), 60 * 30L);
        emailService.sendMail(member.getMemberId(),"[김동근 스프링] 회원가입 인증메일입니다.",VERIFICATION_LINK+uuid.toString());
    }

    @Override
    public void modifyUserRole(Member member, UserRole userRole){
            member.setRole(userRole);
            memberRepository.save(member);
    }

    @Override
    public boolean isPasswordUuidValidate(String key){
        String memberId = redisUtil.getData(key);
        return !memberId.equals("");
    }

    @Override
    public void changePassword(Member member,String password) throws NotFoundException{
        if(member == null) throw new NotFoundException("changePassword(),멤버가 조회되지 않음");
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setMemberPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
    }


    @Override
    public void requestChangePassword(Member member) throws NotFoundException{
        String CHANGE_PASSWORD_LINK = "http://localhost:8080/user/password/";
        if(member == null) throw new NotFoundException("멤버가 조회되지 않음.");
        String key = REDIS_CHANGE_PASSWORD_PREFIX+UUID.randomUUID();
        redisUtil.setDataExpire(key,member.getMemberId(),60 * 30L);
        emailService.sendMail(member.getMemberId(),"[김동근 스프링] 사용자 비밀번호 안내 메일",CHANGE_PASSWORD_LINK+key);
    }


}