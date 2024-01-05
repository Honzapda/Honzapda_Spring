package Honzapda.Honzapda_server.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicUser extends User{

    @Column(nullable = false, length = 50)
    private String account;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;
}
