package com.megait.mymall.service;

import com.megait.mymall.domain.Address;
import com.megait.mymall.domain.Member;
import com.megait.mymall.domain.MemberType;
import com.megait.mymall.validation.SignUpFormValidator;
import com.megait.mymall.validation.SignUpForm;
import com.megait.mymall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member processNewMember(SignUpForm signUpForm) {

        // 올바른 form인 경우 DB 저장
        Member member = Member.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .address(Address.builder()
                        .city(signUpForm.getCity())
                        .street(signUpForm.getStreet())
                        .zip(signUpForm.getZipcode())
                        .build())
                .type(MemberType.ROLE_USER)
                .joinedAt(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        // TODO 이메일에 인증 링크 전송

        // 새로 추가된 회원 (Member 엔티티)를 return
        return member;
    }

    public void login(Member member) {
        // 해당 member를 authenticated 상태로 저장 => 로그인 완료 처리
    }
}
