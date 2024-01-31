package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ShopRepositoryCustom {
    Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByReviewCountDesc(String keyword, Pageable pageable);

    Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByBookmarkCountDesc(String keyword, Pageable pageable);

    List<ShopResponseDto.SearchByNameDto> findSearchByNameDtoByMysqlIds(List<Long> mysqlIds);

    Slice<ShopResponseDto.SearchByNameDto> findByShopNameContaining(String keyword, Pageable pageable);

    List<MapResponseDto.HomeDto> findByMysqlIdIn(List<Long> mysqlIds);

    Slice<MapResponseDto.UserBookmarkShopResponseDto> findBookmarkByUser(User user, Pageable pageable);
}
