package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayCongestionRepository extends JpaRepository<ShopDayCongestion,Long> {
    List<ShopDayCongestion> findAllByShopId(Long shopId);

}
