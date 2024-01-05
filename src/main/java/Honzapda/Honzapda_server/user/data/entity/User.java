package Honzapda.Honzapda_server.user.domain;

import Honzapda.Honzapda_server.user.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String address;

    @Column(length = 50)
    private String address_spec;

    @Column(nullable = false, length = 25)
    private String name;

    private LocalDateTime inactiveDate;
}
