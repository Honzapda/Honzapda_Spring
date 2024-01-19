package Honzapda.Honzapda_server.user.data.entity;

import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignUpType signUpType;

    @Column
    private LocalDateTime inactiveDate;

    @Column
    private String socialToken;

    public enum SignUpType {
        LOCAL, APPLE, GOOGLE, KAKAO
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPrefer> userPrefers = new HashSet<>();

}
