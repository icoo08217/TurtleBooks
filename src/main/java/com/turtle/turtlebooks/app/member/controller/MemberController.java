package com.turtle.turtlebooks.app.member.controller;

import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
            request.getSession().setAttribute("prevPage" , url);
        }
        return "/member/login";
    }

    // 회원 가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin(){
        return "member/join";
    }

    public String join(JoinForm form){

    }

}
