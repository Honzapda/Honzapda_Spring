package Honzapda.Honzapda_server.review.data.entity;

import Honzapda.Honzapda_server.review.data.entity.common.BaseEntity;
import Honzapda.Honzapda_server.user.data.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
    @DecimalMax(value = "5.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
    @Column(nullable = false)
    private Double score;

    @Size(max = 200, message = "본문은 200자까지만  허용됩니다.") // 약 3줄
    @Column(nullable = false)
    private String body;

    @Column(name = "status")
    private boolean status = true;

    //FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;
}
