package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
    List<Shop> findByIdIn(List<Long> mysqlIds);
}
