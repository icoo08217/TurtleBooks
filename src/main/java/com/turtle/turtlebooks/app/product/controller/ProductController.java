package com.turtle.turtlebooks.app.product.controller;

import com.turtle.turtlebooks.app.base.rq.Rq;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import com.turtle.turtlebooks.app.postkeyword.service.PostKeywordService;
import com.turtle.turtlebooks.app.product.dto.ProductForm;
import com.turtle.turtlebooks.app.product.entity.Product;
import com.turtle.turtlebooks.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


}
