package com.megait.mymall.service;

import com.megait.mymall.domain.Address;
import com.megait.mymall.domain.Member;
import com.megait.mymall.domain.MemberType;
import com.megait.mymall.repository.MemberRepository;
import com.megait.mymall.util.ConsoleMailSender;
import com.megait.mymall.validation.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final ConsoleMailSender consoleMailSender;

    private final EmailService emailService;

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
//                .emailCheckToken(UUID.randomUUID().toString())
                .build();

        // JPA Respository로부터 리턴된 Entity 객체는 영속 상태임
        Member newMember = memberRepository.save(member);

        // 회원 인증 이메일 전송
        emailService.sendEmail(newMember);

        // 새로 추가된 회원 (Member 엔티티)를 return
        return newMember;
    }

    public void login(Member member) {
        MemberUser memberUser = new MemberUser(member);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        memberUser,
                        memberUser.getMember().getPassword(),
                        memberUser.getAuthorities()
                );

        SecurityContext ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(token);
    }
}
