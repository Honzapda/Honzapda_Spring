package Honzapda.Honzapda_server.shop.data.entity;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Shop extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Lob
    @Column
    private String description;

    @Lob
    @Column
    private String otherDetails;

    @Column(length = 50)
    private String phoneNumber;

    @DecimalMin(value = "0.0", message = "별점은 0~5 사이여야 합니다.")
    @DecimalMax(value = "5.0", message = "별점은 0~5 사이여야 합니다.")
    @Column(nullable = false)
    private Double rating;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    private String address_spec;

    private LocalDateTime inactiveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}

/*
{
  "name": "Best Coffee Shop",
  "description": "A cozy coffee shop with a variety of blends.",
  "otherDetails": "Free Wi-Fi, Outdoor Seating",
  "phoneNumber": "+1234567890",
  "address": "456 Oak Avenue",
  "address_spec": "Suite 2",
  "userId": 1
}
 */
