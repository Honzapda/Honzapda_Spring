package Honzapda.Honzapda_server.userHelpInfo.repository;

import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHelpInfoImageRepository extends JpaRepository<UserHelpInfoImage, Long> {

    void deleteAllByUserHelpInfo(UserHelpInfo userHelpInfo);
}
