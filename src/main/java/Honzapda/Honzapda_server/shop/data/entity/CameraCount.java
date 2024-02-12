package Honzapda.Honzapda_server.shop.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "camera")
@Getter
@AllArgsConstructor
@Builder
public class CameraCount {
    @MongoId
    private String id;

    private Long shopId;

    private int count;

    private Date expire_at;
}
