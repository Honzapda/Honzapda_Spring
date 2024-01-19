package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static Honzapda.Honzapda_server.review.data.entity.QReview.review;
import static Honzapda.Honzapda_server.shop.data.entity.QShop.shop;
import static Honzapda.Honzapda_server.shop.data.entity.QShopUserBookmark.shopUserBookmark;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Shop> findByShopNameContainingOrderByReviewCountDesc(String keyword, Pageable pageable) {
        List<Tuple> fetch = queryFactory
                .select(shop, review.count())
                .from(review)
                .rightJoin(review.shop, shop)
                .groupBy(shop)
                .where(shopNameContaining(keyword))
                .orderBy(review.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Shop> shops = fetch.stream()
                .map(tuple -> tuple.get(shop))
                .toList();

        return new SliceImpl<>(shops, pageable, fetch.size() == pageable.getPageSize());
    }

    @Override
    public Slice<Shop> findByShopNameContainingOrderByBookmarkCountDesc(String keyword, Pageable pageable) {
        List<Tuple> fetch = queryFactory
                .select(shop, shopUserBookmark.count())
                .from(shopUserBookmark)
                .rightJoin(shopUserBookmark.shop, shop)
                .groupBy(shop)
                .where(shopNameContaining(keyword))
                .orderBy(shopUserBookmark.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Shop> shops = fetch.stream()
                .map(tuple -> tuple.get(shop))
                .toList();

        return new SliceImpl<>(shops, pageable, fetch.size() == pageable.getPageSize());
    }

    private BooleanExpression shopNameContaining(String keyword) {
        return shop.shopName.contains(keyword);
    }
}
