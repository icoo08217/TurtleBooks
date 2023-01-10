package com.turtle.turtlebooks.app.product.service;

import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import com.turtle.turtlebooks.app.postkeyword.service.PostKeywordService;
import com.turtle.turtlebooks.app.product.entity.Product;
import com.turtle.turtlebooks.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PostKeywordService postKeywordService;

    @Transactional
    public Product create(Member author, String subject, int price, long postKeywordId, String productTagContents) {
        PostKeyword postKeyword = postKeywordService.findById(postKeywordId).get();

        return create(author , subject , price , postKeyword , productTagContents);
    }

    @Transactional
    public Product create(Member author, String subject, int price, String postKeywordContent, String productTagContents) {
        PostKeyword postKeyword = postKeywordService.findByContentOrSave(postKeywordContent);

        return create(author, subject, price, postKeyword, productTagContents);
    }

    @Transactional
    public Product create(Member author, String subject, int price, PostKeyword postKeyword, String productTagContents) {
        Product product = Product.builder()
                .author(author)
                .postKeyword(postKeyword)
                .subject(subject)
                .price(price)
                .build();

        productRepository.save(product);

        return product;
    }
}
