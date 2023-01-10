package com.turtle.turtlebooks.app.product.entity;

import com.turtle.turtlebooks.app.AppConfig;
import com.turtle.turtlebooks.app.base.entity.BaseEntity;
import com.turtle.turtlebooks.app.member.entity.Member;
import com.turtle.turtlebooks.app.postkeyword.entity.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseEntity { // 도서 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostKeyword postKeyword;

    private String subject;

    private int price;

    public Product(long id) {
        super(id);
    }

    public int getSalePrice() {
        return getPrice();
    }

    public int getWholesalePrice() { // 도매가 계산
        return (int) Math.ceil(getPrice() * AppConfig.getWholesalePriceRate());
    }

    public boolean isOrderable() {
        return true;
    }

    public String getJdenticon() {
        return "product__" + getId();
    }

    public String getExtra_inputValue_hashTagContents() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("productTags") == false) {
            return "";
        }

        List<ProductTag> productTags = (List<ProductTag>) extra.get("productTags");

        if (productTags.isEmpty()) {
            return "";
        }

        return productTags
                .stream()
                .map(productTag -> "#" + productTag.getProductKeyword().getContent())
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public String getExtra_productTagLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("productTags") == false) {
            return "";
        }

        List<ProductTag> productTags = (List<ProductTag>) extra.get("productTags");

        if (productTags.isEmpty()) {
            return "";
        }

        return productTags
                .stream()
                .map(productTag -> {
                    String text = "#" + productTag.getProductKeyword().getContent();

                    return """
                            <a href="%s" class="text-link">%s</a>
                            """
                            .stripIndent()
                            .formatted(productTag.getProductKeyword().getListUrl(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }

}
