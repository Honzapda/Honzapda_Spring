package Honzapda.Honzapda_server.user.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Prefer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String preferName;

    @OneToMany(mappedBy = "prefer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPrefer> prefers = new HashSet<>();
}
