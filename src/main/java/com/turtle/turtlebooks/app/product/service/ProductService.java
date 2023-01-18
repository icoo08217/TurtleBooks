package com.turtle.turtlebooks.app.product.service;

import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.postTag.entity.PostTag;
import com.turtle.turtlebooks.app.postTag.service.PostTagService;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import com.turtle.turtlebooks.app.postkeyword.service.PostKeywordService;
import com.turtle.turtlebooks.app.product.entity.Product;
import com.turtle.turtlebooks.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PostKeywordService postKeywordService;
    private final PostTagService postTagService;

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

    public Optional<Product> findForPrintById(Long id, Member actor) {
        Optional<Product> opProduct = findById(id);

        if (opProduct.isEmpty()) return opProduct;

        loadForPrintData(opProduct.get(), actor);

        return opProduct;
    }

    private void loadForPrintData(Product product, Member actor) {
        loadForPrintData(List.of(product) , actor);
    }

    private void loadForPrintData(List<Product> products, Member actor) {
        long[] ids = products
                .stream()
                .mapToLong(Product::getId)
                .toArray();

        List<ProductTag> productTagsByProductIds = productTagService.getProductTagsByProductIdIn(ids);

        // 현재 로그인 되어 있고
        // 장바구니에 이미 추가되었는지

        if (actor != null) {
            List<CartItem> cartItems = cartService.getCartItemsByBuyerIdProductIdIn(actor.getId(), ids);

            Map<Long, CartItem> cartItemsByProductIdMap = cartItems
                    .stream()
                    .collect(toMap(
                            cartItem -> cartItem.getProduct().getId(),
                            cartItem -> cartItem
                    ));

            products.stream()
                    .filter(product -> cartItemsByProductIdMap.containsKey(product.getId()))
                    .map(product -> cartItemsByProductIdMap.get(product.getId()))
                    .forEach(cartItem -> cartItem.getProduct().getExtra().put("actor_cartItem", cartItem));
        }

        Map<Long, List<ProductTag>> productTagsByProductIdMap = productTagsByProductIds.stream()
                .collect(groupingBy(
                        productTag -> productTag.getProduct().getId(), toList()
                ));

        products.stream().forEach(product -> {
            List<ProductTag> productTags = productTagsByProductIdMap.get(product.getId());

            if (productTags == null || productTags.size() == 0) return;

            product.getExtra().put("productTags", productTags);
        });
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Post> findPostsByProduct(Product product) {
        Member author = product.getAuthor();
        PostKeyword postKeyword = product.getPostKeyword();
        List<PostTag> postTags = postTagService.getPostTags(author.getId(), postKeyword.getId());

        return postTags.stream()
                .map(PostTag::getPost)
                .collect(Collectors.toList());
    }

    public List<Product> findAllForPrintByOrderByIdDesc(Member actor) {
        List<Product> products = findAllByOrderByIdDesc();

        loadForPrintData(products , actor);

        return products;
    }

    private List<Product> findAllByOrderByIdDesc() {
        return productRepository.findAllByOrderByIdDesc();
    }

    public boolean actorCanModify(Member member, Product product) {
        if (member == null) return false;

        return member.getId() == product.getAuthor().getId();
    }

    public void modify(Product product, String subject, int price, String productTagContents) {
        product.setSubject(subject);
        product.setPrice(price);

        applyProductTags(product, productTagContents);
    }

    @Transactional
    public void applyProductTags(Product product, String productTagContents) {
        productTagService.applyProductTags(product , productTagContents)
    }

    public boolean actorCanRemove(Member member, Product product) {
        return actorCanModify(member, product);
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

    public List<ProductTag> getProductTags(String tagContent, Member member) {

    }
}
