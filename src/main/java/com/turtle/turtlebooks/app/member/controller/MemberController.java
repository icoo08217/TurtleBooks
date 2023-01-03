package com.turtle.turtlebooks.app.member.controller;

import com.turtle.turtlebooks.app.base.dto.RsData;
import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.member.dto.JoinForm;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    // login
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String url = request.getHeader("Referer");
        if (url != null && !url.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", url);
        }
        return "/member/login";
    }

    // 회원 가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm form) {

        memberService.join(form.getUsername(), form.getPassword(), form.getEmail(), form.getNickname());

        return Rq.redirectWithMsg("/member/login", "회원가입이 완료되었습니다.");
    }

    // ID 찾기

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String showFindUsername() {
        return "member/findUsername";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(String email, Model model) {
        Member member = memberService.findByEmail(email).orElse(null);

        if (member == null) {
            return rq.historyBack("일치하는 회원이 존재하지 않습니다.");
        }

        return Rq.redirectWithMsg("/member/login?username=%s".formatted(member.getUsername()), "해당 이메일로 가입한 계정의 아이디는 '%s' 입니다. ".formatted(member.getUsername()));
    }

    // 비밀번호 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/findPassword")
    public String showFindPassword() {
        return "member/findPassword";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findPassword")
    public String findPassword(String username, String email, Model model) {
        Member member = memberService.findByUsernameAndEmail(username, email).orElse(null);

        if (member == null) {
            return rq.historyBack("일치하는 회원이 존재하지 않습니다.");
        }

        RsData sendTempLoginPwToEmailResultData = memberService.sendTempLoginPwToEmailResultData(member);

        if (sendTempLoginPwToEmailResultData.isFail()) {
            return rq.historyBack(sendTempLoginPwToEmailResultData);
        }

        return Rq.redirectWithMsg("/member/login?username=%s".formatted(member.getUsername()), "해당 이메일로 '%s' 님의 임시비밀번호를 발송하였습니다.".formatted(member.getUsername()));
    }

    // 자기 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(Model model) {

        long actorRestCash = memberService.getRestCash(rq.getMember());
        model.addAttribute("actorRestCash", actorRestCash);

        return "member/profile";
    }

    // 비밀번호 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String showModifyPassword(){
        return "member/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(String oldPassword, String newPassword) {
        Member member = rq.getMember();
        RsData rsData = memberService.modifyPassword(member, newPassword, oldPassword);

        if (rsData.isFail()) {
            return rq.historyBack(rsData.getMsg());
        }

        return Rq.redirectWithMsg("/", rsData);
    }
}
