package com.turtle.turtlebooks.app.member.controller;

import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;


}
