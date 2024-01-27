package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserHelpInfoImage {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "userHelpInfoId")
    private UserHelpInfo userHelpInfo;

    @Builder
    private UserHelpInfoImage(String url, UserHelpInfo userHelpInfo) {
        this.url = url;
        this.userHelpInfo = userHelpInfo;
    }

    public static UserHelpInfoImage createImage(String url, UserHelpInfo userHelpInfo) {
        return UserHelpInfoImage.builder()
                .url(url)
                .userHelpInfo(userHelpInfo)
                .build();
    }
}
