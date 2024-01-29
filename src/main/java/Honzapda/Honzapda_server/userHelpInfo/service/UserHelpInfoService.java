package Honzapda.Honzapda_server.userHelpInfo.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoImageConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            userHelpInfoImageRepository.saveAll(
                            UserHelpInfoImageConverter.toImages(requestDto,savedUserHelpInfo));

            return UserHelpInfoConverter.toUserHelpInfoDto(
                    savedUserHelpInfo, getUserHelpInfoImageList(savedUserHelpInfo));
        }
        return UserHelpInfoConverter.toUserHelpInfoDto(savedUserHelpInfo,null);
    }

    public UserHelpInfoResponseDto.UserHelpInfoListDto getUserHelpInfoListDto(Long shopId, Pageable pageable){
        // 어디 shop 도움 정보인지 확인
        Shop findShop = findShopById(shopId);
        // TODO: 좋아요로 바꿔야 함
        Page<UserHelpInfo> findAllByshop = userHelpInfoRepository.findAllByShop(findShop, pageable);
        // 도움정보와 이미지를 매핑하여 userHelpInfoDtos에 저장
        List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoDtos =
                findAllByshop.getContent().stream()
                .map(userHelpInfo -> UserHelpInfoConverter.toUserHelpInfoDto(userHelpInfo, getUserHelpInfoImageList(userHelpInfo)))
                .toList();

        return UserHelpInfoConverter.toUserHelpInfoListDto(findAllByshop, userHelpInfoDtos);
    }
    private Shop findShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
    }
    private List<UserHelpInfoImage> getUserHelpInfoImageList(UserHelpInfo userHelpInfo){
        return userHelpInfoImageRepository
                .findAllByUserHelpInfo(userHelpInfo)
                .orElseThrow(()-> new GeneralException(ErrorStatus.USER_HELP_INFO_NOT_FOUND));
    }

    private void validateDuplicate(User user, Shop shop) {
        userHelpInfoRepository.findByUserAndShop(user, shop)
                .ifPresent(review -> {
                    throw new GeneralException(ErrorStatus.REVIEW_ALREADY_EXIST);
                });
    }
}
