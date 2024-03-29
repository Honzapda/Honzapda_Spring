package Honzapda.Honzapda_server.shop.service.shop;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.review.data.ReviewConverter;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewImageRepository;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.MapConverter;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.*;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopBusinessHourRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopUserBookmarkRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.LikeRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.repository.LikeUserHelpInfoRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {


    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewImageRepository reviewImageRepository;

    private final ShopBusinessHourRepository shopBusinessHourRepository;

    private final ShopUserBookmarkRepository shopUserBookmarkRepository;

    private final UserHelpInfoRepository userHelpInfoRepository;

    private final LikeUserHelpInfoRepository likeUserHelpInfoRepository;

    private final LikeRepository likeRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request){

        if(shopRepository.existsByLoginId(request.getLoginId())){
            throw new GeneralException(ErrorStatus.SHOP_EXIST_MYSQL);
        }

        Shop shop = ShopConverter.toShop(request,passwordEncoder);
        shopRepository.save(shop);

        saveShopBusinessHours(shop.getId(), request.getBusinessHours());

        List<ShopBusinessHour> businessHours = getShopBusinessHours(shop);
        List<ShopResponseDto.BusinessHoursResDTO> businessHoursResDTOS = getShopBusinessHoursResDTO(businessHours);

        return ShopConverter.toShopResponse(shop,businessHoursResDTOS);
    }

    @Override
    public ShopResponseDto.OwnerInfoDto loginShop(ShopRequestDto.LoginDto request) {
        Shop dbOwner = getShopByEMail(request.getLoginId());
        if(!passwordEncoder.matches(request.getPassword(), dbOwner.getPassword()))
            throw new GeneralException(ErrorStatus.PW_NOT_MATCH);

        return ShopConverter.toOwnerInfo(dbOwner);
    }

    @Override
    public ShopResponseDto.SearchDto findShop(Long shopId, Long userId){
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<ShopBusinessHour> businessHours = getShopBusinessHours(shop);
        List<ShopResponseDto.BusinessHoursResDTO> businessHoursResDTOS = getShopBusinessHoursResDTO(businessHours);


        List<ReviewResponseDto.ReviewDto> reviewDtos = getReviewListDto(shop);

        //TODO: Dto에 추가해야함
        List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoListDtoTop2 = getUserHelpInfoListDtoTop2(user, shop);
        ShopResponseDto.SearchDto resultDto = ShopConverter.toShopResponse(shop, businessHoursResDTOS);

        resultDto.setRating(getRating(shopId));
        resultDto.setOpenNow(getOpenNow(businessHours));
        resultDto.setBusinessHours(businessHoursResDTOS);
        resultDto.setUserHelpInfoDtoList(userHelpInfoListDtoTop2);
        resultDto.setReviewList(reviewDtos);

        return resultDto;

    }

    @Override
    public Map<Long, MapResponseDto.HomeDto> findShopsByShopIds(List<Long> mysqlIds) {
        List<MapResponseDto.HomeDto> homeDtos = shopRepository.findByMysqlIdIn(mysqlIds);
        checkOpenNow2(homeDtos);
        return MapConverter.toMapResponseHomeDtoMap(mysqlIds, homeDtos);
    }

    @Override
    public Map<Long, ShopResponseDto.SearchByNameDto> findShopsByShopIdsSorted(List<Long> mysqlIds) {
        List<ShopResponseDto.SearchByNameDto> searchByNameDtos = shopRepository.findSearchByNameDtoByMysqlIds(mysqlIds);
        checkOpenNow(searchByNameDtos);
        return ShopConverter.toSearchResponseMap(mysqlIds, searchByNameDtos);
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContainingSortByReview(ShopRequestDto.SearchDto request, Pageable pageable) {
        Slice<ShopResponseDto.SearchByNameDto> result = shopRepository.findByShopNameContainingOrderByReviewCountDesc(request.getKeyword(), pageable);
        checkOpenNow(result.getContent());
        return result;
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContainingSortByBookmark(ShopRequestDto.SearchDto request, Pageable pageable) {
        Slice<ShopResponseDto.SearchByNameDto> result = shopRepository.findByShopNameContainingOrderByBookmarkCountDesc(request.getKeyword(), pageable);
        checkOpenNow(result.getContent());
        return result;
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> searchShopByShopNameContaining(ShopRequestDto.SearchDto request, Pageable pageable) {
        Slice<ShopResponseDto.SearchByNameDto> result = shopRepository.findByShopNameContaining(request.getKeyword(), pageable);
        checkOpenNow(result.getContent());
        return result;
    }

    private double getRating(Long shopId){
        List<Review> reviewList = reviewRepository.findByShopId(shopId);

        return reviewList.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }

    private Long getReviewCount(Long shopId){
        return reviewRepository.countByShopId(shopId);
    }


    public boolean saveShopBusinessHours(Long shopId, List<ShopRequestDto.BusinessHoursReqDTO> businessHours){

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();

            businessHours.forEach(businessHour -> {
                        boolean isOpen = businessHour.isOpen();
                        ShopBusinessHour shopBusinessHour = ShopBusinessHour.builder()
                                .shop(shop)
                                .isOpen(isOpen)
                                .openHours(isOpen ? businessHour.getOpenHours() : null)
                                .closeHours(isOpen ? businessHour.getCloseHours() : null)
                                .dayOfWeek(businessHour.getDayOfWeek())
                                .build();

                        shopBusinessHourRepository.save(shopBusinessHour);
                    });

            return true;
        } else {
            throw new GeneralException(ErrorStatus.SHOP_NOT_FOUND);
        }
    }

    public List<ShopBusinessHour> getShopBusinessHours(Shop shop) {
        return shopBusinessHourRepository.findShopBusinessHourByShop(shop);
    }

    public List<ShopResponseDto.BusinessHoursResDTO> getShopBusinessHoursResDTO(List<ShopBusinessHour> businessHours) {
        return businessHours.stream()
                .map(ShopConverter::toShopBusinessHourDto)
                .collect(Collectors.toList());
    }



    public boolean getOpenNow(List<ShopBusinessHour> businessHours) {
        return businessHours.stream()
                .anyMatch(shopBusinessHour -> shopBusinessHour.getDayOfWeek().equalsIgnoreCase(getCurrentDayOfWeek())
                        && shopBusinessHour.isOpen()
                        && isCurrentTimeWithinOpenHours(shopBusinessHour.getOpenHours(), shopBusinessHour.getCloseHours()));
    }

    private boolean isCurrentTimeWithinOpenHours(String openHours, String closeHours) {
        LocalTime currentTime = LocalTime.now();
        LocalTime openTime = LocalTime.parse(openHours);
        LocalTime closeTime = LocalTime.parse(closeHours);

        return currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);
    }

    private Shop getShopByEMail(String email){
        return shopRepository.findByLoginId(email)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    private String getCurrentDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek().name();
    }

    private List<ReviewResponseDto.ReviewDto> getReviewListDto(Shop shop) {

        List<Review> reviewList = reviewRepository.findTop3ByShopOrderByVisitedAtDesc(shop);

        return reviewList.stream()
                .map(review -> {
                    List<ReviewImage> reviewImages = reviewImageRepository
                            .findAllByReview(review).orElseThrow(()-> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
                    return ReviewConverter.toReviewDto(review, reviewImages);
                })
                .collect(Collectors.toList());
    }
    private List<UserHelpInfoResponseDto.UserHelpInfoDto> getUserHelpInfoListDtoTop2(User user, Shop shop) {

        return userHelpInfoRepository.findAllByShop(shop)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_HELP_INFO_NOT_FOUND))
                .stream()
                .map(userHelpInfo->{
                    Long likeCount = likeUserHelpInfoRepository.countByUserHelpInfo(userHelpInfo);
                    boolean userLike = likeUserHelpInfoRepository.existsByUserAndUserHelpInfo(user,userHelpInfo);
                    return UserHelpInfoConverter.toUserHelpInfoDto(userHelpInfo,likeCount,userLike);
                })
                // likeCount를 기준으로 내림차순으로 정렬
                .sorted((info1, info2) -> Long.compare(info2.getLike().getLikeCount(), info1.getLike().getLikeCount()))
                .limit(2)
                .toList();
    }

    private boolean isUserLikeShop(User user, Shop shop) {
        return likeRepository.existsByShopAndUser(shop, user);
    }


    private void checkOpenNow(List<ShopResponseDto.SearchByNameDto> dtos) {
        dtos.forEach(
                dto -> {
                    if (dto != null) {
                        Optional.ofNullable(dto.getShopBusinessHour()).ifPresentOrElse(
                                shopBusinessHour -> {
                                    if (shopBusinessHour.isOpen())
                                        dto.setOpenNow(isCurrentTimeWithinOpenHours(shopBusinessHour.getOpenHours(), shopBusinessHour.getCloseHours()));
                                    else dto.setOpenNow(false);
                                },
                                () -> dto.setOpenNow(false)
                        );
                    }
                }
        );
    }

    private void checkOpenNow2(List<MapResponseDto.HomeDto> dtos) {
        dtos.forEach(
                dto -> {
                    if (dto != null) {
                        Optional.ofNullable(dto.getShopBusinessHour()).ifPresentOrElse(
                                shopBusinessHour -> {
                                    if (shopBusinessHour.isOpen())
                                        dto.setOpenNow(isCurrentTimeWithinOpenHours(shopBusinessHour.getOpenHours(), shopBusinessHour.getCloseHours()));
                                    else dto.setOpenNow(false);
                                },
                                () -> dto.setOpenNow(false)
                        );
                    }
                }
        );
    }

    public Long getShopBookMarkCount(Shop shop) {
        return shopUserBookmarkRepository.countBookmarksByShop(shop);
    }

    public Long getShopReviewCount(Shop shop) {
        return reviewRepository.countReviewsByShop(shop);
    }


}
