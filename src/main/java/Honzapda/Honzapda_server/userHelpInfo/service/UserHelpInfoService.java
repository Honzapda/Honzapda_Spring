package Honzapda.Honzapda_server.userHelpInfo.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto.UserHelpInfoCreateRequest;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserHelpInfoService {

    private final ShopRepository shopRepository;
    private final UserHelpInfoRepository userHelpInfoRepository;
    private final UserHelpInfoImageRepository userHelpInfoImageRepository;

    @Transactional
    public ReviewResponseDto.ReviewDto registerUserHelpInfo(Long userId, Long shopId, UserHelpInfoCreateRequest requestDto) {

        User user = User.builder().id(userId).build();
        Shop shop = findShopById(shopId);

        // 리뷰 중복 방지
        validateDuplicate(user, shop);

        UserHelpInfo savedUserHelpInfo = userHelpInfoRepository.save(requestDto.toEntity(user, shop));

        // TODO: url의 유효성 검증
        if (!requestDto.getImageUrls().isEmpty()) {
            userHelpInfoImageRepository.saveAll(requestDto.toImageEntity(savedUserHelpInfo));
        }

        return null;
    }

    private Shop findShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
    }

    private void validateDuplicate(User user, Shop shop) {
        userHelpInfoRepository.findByUserAndShop(user, shop)
                .ifPresent(review -> {
                    throw new GeneralException(ErrorStatus.REVIEW_ALREADY_EXIST);
                });
    }
}
