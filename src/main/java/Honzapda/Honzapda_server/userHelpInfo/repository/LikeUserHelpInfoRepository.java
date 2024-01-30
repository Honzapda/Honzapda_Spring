package Honzapda.Honzapda_server.userHelpInfo.repository;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.mapping.LikeUserHelpInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeUserHelpInfoRepository extends JpaRepository<LikeUserHelpInfo,Long> {
    Boolean existsByUserAndUserHelpInfo(User user, UserHelpInfo userHelpInfo);
    Optional<LikeUserHelpInfo> findByUserAndUserHelpInfo(User user, UserHelpInfo userHelpInfo);
    Long countByUserHelpInfo(UserHelpInfo userHelpInfo);
    void deleteAllByUserHelpInfo(UserHelpInfo userHelpInfo);
}
