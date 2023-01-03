package com.turtle.turtlebooks.app.member.service;

import com.turtle.turtlebooks.app.AppConfig;
import com.turtle.turtlebooks.app.base.dto.RsData;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.member.exception.AlreadyJoinException;
import com.turtle.turtlebooks.app.member.repository.MemberRepository;
import com.turtle.turtlebooks.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(String username, String password, String email, String nickname) {
        if ( memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        // 이메일 처리 추후 기능 구현

        return member;
    }


    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByUsernameAndEmail(String username, String email) {
        return memberRepository.findByUsernameAndEmail(username, email);
    }

    public RsData sendTempLoginPwToEmailResultData(Member actor) {
        String title = AppConfig.getSiteName() + "]임시 패스워드 발송";
        String tempPassword = Ut.getTempPassword(6);
        String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
        body += "<a href=\"" + AppConfig.getSiteBaseUrl() + "/member/login\" target=\"_blank\">로그인 하러가기</a>";

        RsData sendResultData = emailService.sendEmail(actor.getEmail(), title, body);

        if (sendResultData.isFail()) {
            return sendResultData;
        }

        setTempPassword(actor, tempPassword);

        return RsData.of("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
    }

    private void setTempPassword(Member actor, String tempPassword) {
        actor.setPassword(passwordEncoder.encode(tempPassword));
    }

    public long getRestCash(Member member) {
        return memberRepository.findById(member.getId()).get().getRestCash();
    }
}
