package Honzapda.Honzapda_server.user.data.entity;

import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50)
    private String address;

    @Column(length = 50)
    private String address_spec;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(nullable = false, length = 25)
    private String name;

    private LocalDateTime inactiveDate;
}

/*
{
  "email": "example22@example.com",
  "password": "password123",
  "address": "123 Main St",
  "address_spec": "Apt 456",
  "socialType": "APPLE",
  "memberType": "BASIC",
  "name": "John Doe22"
}
 */