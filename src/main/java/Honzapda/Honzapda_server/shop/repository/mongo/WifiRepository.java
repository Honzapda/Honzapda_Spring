package Honzapda.Honzapda_server.shop.repository.mongo;

import Honzapda.Honzapda_server.shop.data.entity.WifiCount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WifiRepository extends MongoRepository<WifiCount,String> {

    Optional<WifiCount> findByShopId(Long shopId);
}
