package com.turtle.turtlebooks.app.base.rq;

import com.turtle.turtlebooks.app.member.entity.Member;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequestScope
public class Rq {

    private final HttpServletRequest req;
    private final HttpServletResponse resp;
//    private final MemberContext memberContext;
    @Getter
    private final Member member;

    public Rq(HttpServletRequest req, HttpServletResponse resp, Member member) {
        this.req = req;
        this.resp = resp;

        //현재 로그인한 회원의 인증정보를 가져옴.
        this.member = member;
    }

    public boolean isLogined() {
        return isLogout() == false;
    }

    public boolean isLogout() {
        return member == null;
    }

    public long getId() {
        if ( isLogout()) {
            return 0;
        }
        return getMember().getId();
    }
}
