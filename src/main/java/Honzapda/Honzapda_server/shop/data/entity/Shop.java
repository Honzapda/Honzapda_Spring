package Honzapda.Honzapda_server.shop.data.entity;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
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
    private String shopName;

    @Column(nullable = false)
    private String adminName;

    @Lob
    @Column
    private String description;

    @Lob
    @Column
    private String otherDetails;

    @Column(length = 50)
    private String shopPhoneNumber;

    @Column(length = 50)
    private String adminPhoneNumber;

    @Column(nullable = false)
    private Double rating;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    private String address_spec;

    @Column
    private String businessNumber;

    private LocalDateTime inactiveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
