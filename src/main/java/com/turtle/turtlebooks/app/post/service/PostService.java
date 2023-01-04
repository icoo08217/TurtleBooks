package com.turtle.turtlebooks.app.post.service;

import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.post.repository.PostRepository;
import com.turtle.turtlebooks.app.postTag.entity.PostTag;
import com.turtle.turtlebooks.app.postTag.service.PostTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostTagService postTagService;

    @Transactional
    public Post write(Member author, String subject, String content, String contentHtml, String postTagContents) {
        Post post = Post.builder()
                .subject(subject)
                .content(content)
                .contentHtml(contentHtml)
                .author(author)
                .build();

        postRepository.save(post);

        applyPostTags(post, postTagContents);

        return post;
    }

    private void applyPostTags(Post post, String postTagContents) {
        postTagService.applyPostTags(post, postTagContents);
    }

    public Optional<Post> findForPrintById(long id) {
        Optional<Post> opPost = findById(id);
        
        if (opPost.isEmpty()) return opPost;

        List<PostTag> postTags = getPostTags(opPost.get());

        opPost.get().getExtra().put("postTags", postTags);

        return opPost;
    }

    private List<PostTag> getPostTags(Post post) {
        return postTagService.getPostTags(post);
    }

    public Optional<Post> findById(long postId) {
        return postRepository.findById(postId);
    }

    // 글 수정시 작성자 확인
    public boolean actorCanModify(Member author, Post post) {
        return author.getId() == post.getAuthor().getId();
    }

    public void modify(Post post, String subject, String content, String contentHtml, String postTagContents) {
        post.setSubject(subject);
        post.setContent(content);
        post.setContentHtml(contentHtml);
        applyPostTags(post , postTagContents);
    }

    public List<Post> findAllForPrintByAuthorIdOrderByIdDesc(long authorId) {
        List<Post> posts = postRepository.findAllByAuthorIdOrderByIdDesc(authorId);

        loadForPrintData(posts);

        return posts;
    }

    public void loadForPrintData(List<Post> posts) {
        long[] ids = posts
                .stream()
                .mapToLong(Post::getId)
                .toArray();

        List<PostTag> postTagsByPostIds = postTagService.getPostTagsByPostIdIn(ids);

        Map<Long, List<PostTag>> postTagsByPostIdsMap = postTagsByPostIds.stream()
                .collect(groupingBy(
                        postTag -> postTag.getPost().getId(), toList()
                ));

        posts.stream().forEach(post -> {
            List<PostTag> postTags = postTagsByPostIdsMap.get(post.getId());

            if (postTags == null || postTags.size() == 0) return;

            post.getExtra().put("postTags", postTags);
        });
    }

    public List<Post> getPostTags(Member author, String postKeywordContent) {
        List<Post> postTags = postTagService.getPostTags(author, postKeywordContent);

        loadForPrintDataOnPostTagList(postTags);
        
        return postTags;
    }

    private void loadForPrintDataOnPostTagList(List<Post> postTags) {
        postTags.stream()
                .map(PostTag::getPost)
                .collect(toList());

        loadForPrintData(postTags);
    }

    public boolean actorCanRemove(Member author, Post post) {
        return author.getId() == post.getAuthor().getId();
    }

    public void remove(Post post) {
        postRepository.delete(post);
    }
}
