package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopPhotoRepository extends JpaRepository<ShopPhoto, Long> {

    List<ShopPhoto> findShopPhotosByShop(Shop shop);
}
