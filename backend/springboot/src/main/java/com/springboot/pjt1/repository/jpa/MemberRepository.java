package com.springboot.pjt1.repository.jpa;

import org.springframework.data.repository.CrudRepository;

import com.springboot.pjt1.repository.jpa.dto.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
	Member findByMemberId(String username);

}
