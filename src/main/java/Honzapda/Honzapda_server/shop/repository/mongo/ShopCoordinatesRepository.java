package Honzapda.Honzapda_server.shop.repository.mongo;

import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShopCoordinatesRepository extends MongoRepository<ShopCoordinates, String> {
    Optional<ShopCoordinates> findByMysqlId(Long shopId);

    List<ShopCoordinates> findByLocationNear(Point point, Distance distance);

    Page<ShopCoordinates> findByShopNameContainingAndLocationNear(String shopName, Point point, Distance distance, Pageable pageable);

    boolean existsByMysqlId(Long shopId);
}
