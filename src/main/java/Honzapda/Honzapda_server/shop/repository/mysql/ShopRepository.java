package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

    boolean existsByLoginId(String LoginId);
    Optional<Shop> findByLoginId(String email);
}
