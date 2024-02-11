package Honzapda.Honzapda_server.shop.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopBusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @Column(nullable = false)
    private boolean isOpen;

    @Column(nullable = false)
    private String dayOfWeek;

    private String openHours;

    private String closeHours;

}
