package Honzapda.Honzapda_server.shop.data.entity;

import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "wifi")
@Getter
@AllArgsConstructor
@Builder
public class WifiCount {
    @MongoId
    private String id;

    private Long shopId;

    private int count;

    private Date expire_at;
}
