package Honzapda.Honzapda_server.userHelpInfo.repository;

import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHelpInfoImageRepository extends JpaRepository<UserHelpInfoImage, Long> {
    //TODO:좋아요 추가해서, 좋아요 수로 변경해야함
    List<UserHelpInfoImage> findAllByUserHelpInfo(UserHelpInfo userHelpInfo);
}
