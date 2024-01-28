package Honzapda.Honzapda_server.userHelpInfo.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoImageConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserHelpInfoService {

    private final ShopRepository shopRepository;
    private final UserHelpInfoRepository userHelpInfoRepository;
    private final UserHelpInfoImageRepository userHelpInfoImageRepository;

    @Transactional
    public UserHelpInfoResponseDto.UserHelpInfoDto registerUserHelpInfo(
            Long userId, Long shopId, UserHelpInfoRequestDto.CreateDto requestDto) {

        User user = User.builder().id(userId).build();
        Shop shop = findShopById(shopId);

        // 리뷰 중복 방지
        // validateDuplicate(user, shop);

        UserHelpInfo savedUserHelpInfo = userHelpInfoRepository.save(
                UserHelpInfoConverter.toEntity(requestDto,user,shop));

        // TODO: url의 유효성 검증
        if (!requestDto.getImageUrls().isEmpty()) {
            List<UserHelpInfoImage> userHelpInfoImages = userHelpInfoImageRepository.saveAll(
                            UserHelpInfoImageConverter.toImages(requestDto,savedUserHelpInfo));

            List<UserHelpInfoImageResponseDto.ImageDto> imageDtos = userHelpInfoImageRepository.findAllByUserHelpInfo(savedUserHelpInfo).stream()
                            .map(UserHelpInfoImageConverter::toImageDto)
                            .toList();

            return UserHelpInfoConverter.toUserHelpInfoDto(savedUserHelpInfo,imageDtos);
        }
        return UserHelpInfoConverter.toUserHelpInfoDto(savedUserHelpInfo,null);
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
