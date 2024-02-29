package Honzapda.Honzapda_server.shop.repository.mongo;

import Honzapda.Honzapda_server.shop.data.entity.CameraCount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CameraRepository extends MongoRepository<CameraCount,String> {
    Optional<CameraCount> findByShopId(Long shopId);

}
