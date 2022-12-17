package com.turtle.turtlebooks.app.post.service;

import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAllForPrintByAuthorIdOrderByIdDesc(long authorId) {
        List<Post> posts = postRepository.findAllByAuthorIdOrderByIdDesc(authorId);

        return posts;
    }

}
