package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserHelpInfoImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "userHelpInfoId")
    private UserHelpInfo userHelpInfo;
    
}
