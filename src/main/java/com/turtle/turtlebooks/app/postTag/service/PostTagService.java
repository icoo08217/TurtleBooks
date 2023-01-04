package com.turtle.turtlebooks.app.postTag.service;

import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.postTag.entity.PostTag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {


    public void applyPostTags(Post post, String postTagContents) {

    }

    public List<PostTag> getPostTagsByPostIdIn(long[] ids) {
    }

    public List<Post> getPostTags(Member author, String postKeywordContent) {
    }
}
