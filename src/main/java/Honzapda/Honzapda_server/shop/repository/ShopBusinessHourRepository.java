package Honzapda.Honzapda_server.shop.repository;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopBusinessHourRepository extends JpaRepository<ShopBusinessHour, Long> {
    List<ShopBusinessHour> findShopBusinessHourByShop(Shop shop);
}
