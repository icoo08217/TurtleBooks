package com.turtle.turtlebooks.app.home.controller;

import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final Rq rq;
    private final PostService postService;

    @GetMapping("/")
    public String showMain(Model model) {
        if ( rq.isLogined() ) {
            List<Post> posts = postService.findAllForPrintByAuthorIdOrderByIdDesc(rq.getId());
            model.addAttribute("posts", posts);
        }

        return "home/main";
    }
}
