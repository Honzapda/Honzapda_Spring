package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.ShopAverageCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AverageCongestionRepository extends JpaRepository<ShopAverageCongestion,Long> {
    List<ShopAverageCongestion> findAllByShopId(Long shopId);
}
