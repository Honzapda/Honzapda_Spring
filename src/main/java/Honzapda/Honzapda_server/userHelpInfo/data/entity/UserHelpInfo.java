package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHelpInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDate;

    // 혼잡도: 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%
    @Enumerated(EnumType.STRING)
    private Congestion congestion;

    // 앉았던 책상의 넓이: 넓었어요, 적당했어요, 좁았어요, 기억나지 않아요
    @Enumerated(EnumType.STRING)
    private DeskSize deskSize;

    // 콘센트 개수: 넉넉했어요, 적당했어요, 부족했어요, 기억나지 않아요
    @Enumerated(EnumType.STRING)
    private OutletCount outletCount;

    // 카페 조명은 어떻게 느껴졌나요?: 밝았어요, 적당했어요, 어두웠어요, 기억나지 않아요
    @Enumerated(EnumType.STRING)
    private Light light;

    // 콘센트는 주로 어디에 있었나요?: 직접 입력, 기억나지 않아요
    // @Lob
    private String outletLocation;

    // 화장실은 어디에 있었나요?: 직접 입력, 기억나지 않아요
    private String restroomLocation;

    // 카페에서 어떤 종류의 노래가 많이 나왔나요?: 직접 입력, 기억나지 않아요
    private String musicGenre;

    // 카페의 전체적인 분위기는 어떻게 느껴졌나요?: 직접 입력, 기억나지 않아요
    private String atmosphere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shop;

    @Builder
    private UserHelpInfo(LocalDateTime visitDate, Congestion congestion, DeskSize deskSize, OutletCount outletCount, Light light, String outletLocation, String restroomLocation, String musicGenre, String atmosphere, User user, Shop shop) {
        this.visitDate = visitDate;
        this.congestion = congestion;
        this.deskSize = deskSize;
        this.outletCount = outletCount;
        this.light = light;
        this.outletLocation = outletLocation;
        this.restroomLocation = restroomLocation;
        this.musicGenre = musicGenre;
        this.atmosphere = atmosphere;
        this.user = user;
        this.shop = shop;
    }
}
