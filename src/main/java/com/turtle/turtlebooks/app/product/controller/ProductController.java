package com.turtle.turtlebooks.app.product.controller;

import com.turtle.turtlebooks.app.base.exception.ActorCanNotModifyException;
import com.turtle.turtlebooks.app.base.exception.ActorCanNotRemoveException;
import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.post.entity.Post;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import com.turtle.turtlebooks.app.postkeyword.service.PostKeywordService;
import com.turtle.turtlebooks.app.product.dto.ProductForm;
import com.turtle.turtlebooks.app.product.dto.ProductModifyForm;
import com.turtle.turtlebooks.app.product.entity.Product;
import com.turtle.turtlebooks.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final Rq rq;
    private final PostKeywordService postKeywordService;
    private final ProductService productService;

    @PreAuthorize("isAuthenticated() and hasAnyAuthority('AUTHOR')")
    @GetMapping("/create")
    public String showCreate(Model model) {
        List<PostKeyword> postKeywords = postKeywordService.findByMemberId(rq.getId());
        model.addAttribute("postKeywords", postKeywords);
        return "product/create";
    }

    @PreAuthorize("isAuthenticated() and hasAnyAuthority('AUTHOR')")
    @PostMapping("/create")
    public String create(@Valid ProductForm productForm) {
        Member author = rq.getMember();
        Product product = productService.create(author, productForm.getSubject(), productForm.getPrice(), productForm.getPostKeywordId(), productForm.getProductTagContents());
        return "redirect:/product/" + product.getId();
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Product product = productService.findForPrintById(id, rq.getMember()).get();
        List<Post> posts = productService.findPostsByProduct(product);

        model.addAttribute("product", product);
        model.addAttribute("posts", posts);

        return "product/detail";
    }

    @GetMapping("list")
    public String showList(Model model) {
        List<Product> products = productService.findAllForPrintByOrderByIdDesc(rq.getMember());

        model.addAttribute("products", products);

        return "product/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@PathVariable Long id , Model model){
        Product product = productService.findForPrintById(id, rq.getMember()).get();

        // 현재 회원이 상품을 수정할 수 있는 사람인지 확인
        if (productService.actorCanModify(rq.getMember() , product) == false){
            throw new ActorCanNotModifyException();
        }
        model.addAttribute("product", product);

        return "product/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@Valid ProductModifyForm productForm, @PathVariable Long id) {
        Product product = productService.findById(id).get();
        Member member = rq.getMember();

        if (productService.actorCanModify(rq.getMember() , product) == false){
            throw new ActorCanNotModifyException();
        }

        productService.modify(product, productForm.getSubject(), productForm.getPrice(), productForm.getProductTagContents());
        return Rq.redirectWithMsg("/product/" + product.getId(), "%d 번 도서 상품이 수정 완료되었습니다.".formatted(product.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/remove")
    public String remove(@PathVariable Long id) {
        Product product = productService.findById(id).get();
        Member member = rq.getMember();

        if(productService.actorCanRemove(member , product) == false) {
            throw new ActorCanNotRemoveException();
        }

        productService.remove(product);

        return Rq.redirectWithMsg("/post/list", "%번 글이 삭제되었습니다.".formatted(product.getId()));
    }

}
