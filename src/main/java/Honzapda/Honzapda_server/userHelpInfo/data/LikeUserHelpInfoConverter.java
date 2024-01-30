package Honzapda.Honzapda_server.userHelpInfo.data;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.mapping.LikeUserHelpInfo;

public class LikeUserHelpInfoConverter {

    public static LikeUserHelpInfo toLikeUserHelpInfoEntity(User user, UserHelpInfo userHelpInfo){
        return LikeUserHelpInfo.builder()
                .userHelpInfo(userHelpInfo)
                .user(user)
                .build();
    }
}
