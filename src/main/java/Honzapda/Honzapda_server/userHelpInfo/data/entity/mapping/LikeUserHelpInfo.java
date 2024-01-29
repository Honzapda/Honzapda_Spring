package Honzapda.Honzapda_server.userHelpInfo.data.entity.mapping;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.common.BaseEntity;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeUserHelpInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "like_article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_help_info_id",nullable = false)
    private UserHelpInfo userHelpInfo;

}
