package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ShopRepositoryCustom {
    Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByReviewCountDesc(String keyword, Pageable pageable);

    Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByBookmarkCountDesc(String keyword, Pageable pageable);

    List<ShopResponseDto.SearchByNameDto> findSearchByNameDtoByMysqlIds(List<Long> mysqlIds);
}
