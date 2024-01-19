package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ShopRepositoryCustom {
    Slice<Shop> findByShopNameContainingOrderByReviewCountDesc(String keyword, Pageable pageable);

    Slice<Shop> findByShopNameContainingOrderByBookmarkCountDesc(String keyword, Pageable pageable);
}
