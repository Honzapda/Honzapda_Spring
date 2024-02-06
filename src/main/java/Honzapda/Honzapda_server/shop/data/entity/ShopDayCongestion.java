package Honzapda.Honzapda_server.shop.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDayCongestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @Column
    private String dayOfWeek;

    @Column
    private CongestionLevel congestionLevel;

    public enum CongestionLevel {
        COMFORTABLE,
        NORMAL,
        BUSY
    }

}
