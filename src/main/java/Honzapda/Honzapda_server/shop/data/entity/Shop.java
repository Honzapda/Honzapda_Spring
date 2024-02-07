package Honzapda.Honzapda_server.shop.data.entity;

import Honzapda.Honzapda_server.common.dto.SignUpType;
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
    private String stationDistance;

    @Column
    private String businessNumber;

    @Column
    private String loginId;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignUpType signUpType;

    @Column
    private LocalDateTime inactiveDate;

    @Column
    private String shopMainImage;

    @Column
    private Long totalSeatCount;

    // TODO: 영업 시간 및 휴무일 추가

    // TODO: 유저가 저장한 가게 목록에 대한 테이블 생성 필요

}
