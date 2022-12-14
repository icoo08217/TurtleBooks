package com.turtle.turtlebooks.app.postkeyword.service;

import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import com.turtle.turtlebooks.app.postkeyword.repository.PostKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostKeywordService {

    private final PostKeywordRepository postKeywordRepository;

    public PostKeyword save(String content) {
        Optional<PostKeyword> optKeyword = postKeywordRepository.findByContent(content);

        if (optKeyword.isPresent()) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword.builder()
                .content(content)
                .build();

        postKeywordRepository.save(postKeyword);

        return postKeyword;
    }


    public List<PostKeyword> findByMemberId(long authorId) {
        return postKeywordRepository.getQslAllByAuthorId(authorId);
    }

    public Optional<PostKeyword> findById(Long id) {
        return postKeywordRepository.findById(id);
    }

    public PostKeyword findByContentOrSave(String content) {
        return save(content);
    }
}
