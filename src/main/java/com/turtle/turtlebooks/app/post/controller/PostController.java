package com.turtle.turtlebooks.app.post.controller;

import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Rq rq;
}
