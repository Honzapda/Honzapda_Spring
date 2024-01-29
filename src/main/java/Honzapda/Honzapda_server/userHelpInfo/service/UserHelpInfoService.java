package Honzapda.Honzapda_server.userHelpInfo.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.LikeUserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoImageConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoImageResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoRequestDto;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.mapping.LikeUserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.repository.LikeUserHelpInfoRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final LikeUserHelpInfoRepository likeUserHelpInfoRepository;

    @Transactional
    public UserHelpInfoResponseDto.UserHelpInfoDto registerUserHelpInfo(
            Long userId, Long shopId, UserHelpInfoRequestDto.CreateDto requestDto) {

        User user = User.builder().id(userId).build();
        Shop shop = findShopById(shopId);

        // 리뷰 중복 방지
        // validateDuplicate(user, shop);

        UserHelpInfo savedUserHelpInfo = userHelpInfoRepository.save(
                UserHelpInfoConverter.toEntity(requestDto,user,shop));

        Long likeCount = likeUserHelpInfoRepository.countByUserHelpInfo(savedUserHelpInfo);

        // TODO: url의 유효성 검증
        if (!requestDto.getImageUrls().isEmpty()) {
            userHelpInfoImageRepository.saveAll(
                            UserHelpInfoImageConverter.toImages(requestDto,savedUserHelpInfo,shop));

            return UserHelpInfoConverter.toUserHelpInfoDto(
                    savedUserHelpInfo, getUserHelpInfoImageList(savedUserHelpInfo),likeCount);
        }
        return UserHelpInfoConverter.toUserHelpInfoDto(savedUserHelpInfo,null,likeCount);
    }
    @Transactional
    public void likeUserHelpInfo(Long userId, Long userHelpInfoId){
        User user = User.builder().id(userId).build();
        UserHelpInfo userHelpInfo = getLikeUserHelpInfo(userHelpInfoId);
        // 이미 좋아요를 눌렀는지 검사
        if(likeUserHelpInfoRepository.existsByUserAndUserHelpInfo(user,userHelpInfo))
            throw new GeneralException(ErrorStatus.LIKE_ALREADY_LIKED);

        likeUserHelpInfoRepository.save(
                LikeUserHelpInfoConverter.toLikeUserHelpInfoEntity(user,userHelpInfo));
    }

    @Transactional
    public void deleteLikeUserHelpInfo(Long userId, Long userHelpInfoId){
        User user = User.builder().id(userId).build();
        UserHelpInfo userHelpInfo = getLikeUserHelpInfo(userHelpInfoId);
        // 이미 좋아요를 눌렀는지 검사
        LikeUserHelpInfo likeUserHelpInfo = likeUserHelpInfoRepository.findByUserAndUserHelpInfo(user, userHelpInfo)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LIKE_NOT_FOUND));

        likeUserHelpInfoRepository.delete(likeUserHelpInfo);
    }

    public UserHelpInfoResponseDto.UserHelpInfoListDto getUserHelpInfoListDto(Long shopId, Pageable pageable){
        // 어디 shop 도움 정보인지 확인
        Shop findShop = findShopById(shopId);
        // 정렬과 무관하게 shop을 기준으로 조회
        Page<UserHelpInfo> findAllByShop = userHelpInfoRepository.findAllByShop(findShop, pageable);

        // 도움정보와 이미지를 매핑하여 userHelpInfoDtos에 저장
        List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoDtos =
                findAllByShop.getContent().stream().map(userHelpInfo -> {
                        // 좋아요 갯수
                        Long likeCount = likeUserHelpInfoRepository.countByUserHelpInfo(userHelpInfo);

                        return UserHelpInfoConverter.toUserHelpInfoDto(
                            userHelpInfo, getUserHelpInfoImageList(userHelpInfo),likeCount);
                        }).
                        sorted((info1, info2) -> {
                            // likeCount를 기준으로 내림차순으로 정렬
                            return Long.compare(info2.getLikeCount(), info1.getLikeCount());
                        })
                        .toList();

        return UserHelpInfoConverter.toUserHelpInfoListDto(findAllByShop, userHelpInfoDtos);
    }

    public UserHelpInfoImageResponseDto.ImageListDto getUserHelpInfoImageListDto(Long shopId, Pageable pageable){

        Shop findshop = findShopById(shopId);
        Slice<UserHelpInfoImage> allByShop = userHelpInfoImageRepository.findAllByShopOrderByIdDesc(findshop,pageable);

        return UserHelpInfoImageConverter.toImageListDto(allByShop);
    }

    private Shop findShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
    }
    private UserHelpInfo getLikeUserHelpInfo(Long userHelpInfoId){
        return userHelpInfoRepository.findById(userHelpInfoId)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_HELP_INFO_NOT_FOUND));
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
