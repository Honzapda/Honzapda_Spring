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
import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.data.entity.ShopPhoto;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopBusinessHourRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopPhotoRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import Honzapda.Honzapda_server.userHelpInfo.data.UserHelpInfoConverter;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfoImage;
import Honzapda.Honzapda_server.userHelpInfo.repository.LikeUserHelpInfoRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopServiceImpl implements ShopService {


    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    private final ShopPhotoRepository shopPhotoRepository;

    private final ReviewImageRepository reviewImageRepository;

    private final ShopBusinessHourRepository shopBusinessHourRepository;

    private final UserHelpInfoRepository userHelpInfoRepository;

    private final UserHelpInfoImageRepository userHelpInfoImageRepository;

    private final LikeUserHelpInfoRepository likeUserHelpInfoRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request){

        Shop shop = ShopConverter.toShop(request,passwordEncoder);
        shopRepository.save(shop);

        List<String> photoUrls = request.getPhotoUrls();
        saveShopPhoto(shop.getId(), photoUrls);

        List<ShopRequestDto.BusinessHoursReqDTO> businessHours = request.getBusinessHours();
        saveShopBusinessHours(shop.getId(), businessHours);

        return ShopConverter.toShopResponse(shop);
    }
    @Override
    public ShopResponseDto.SearchDto findShop(Long shopId){
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isPresent()) {

            Shop shop = optionalShop.get();
            List<String> photoUrls = getShopPhotoUrls(shop);
            List<ShopBusinessHour> businessHours = getShopBusinessHours(shop);
            List<ShopResponseDto.BusinessHoursResDTO> businessHoursResDTOS = getShopBusinessHoursResDTO(businessHours);
            List<ReviewResponseDto.ReviewDto> reviewDtos = getReviewListDto(shop);
            //TODO: Dto에 추가해야함
            List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoListDtoTop2 = getUserHelpInfoListDtoTop2(shop);
            ShopResponseDto.SearchDto resultDto = ShopConverter.toShopResponse(shop);

            resultDto.setRating(getRating(shopId));
            resultDto.setOpenNow(getOpenNow(businessHours));
            resultDto.setPhotoUrls(photoUrls);
            resultDto.setBusinessHours(businessHoursResDTOS);
            resultDto.setUserHelpInfoDtoList(userHelpInfoListDtoTop2);
            resultDto.setReviewList(reviewDtos);

            return resultDto;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
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

    public boolean saveShopPhoto(Long shopId, List<String> photoUrls){

        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();

            photoUrls.stream()
                    .forEach(photoUrl ->{
                        ShopPhoto shopPhoto = ShopPhoto.builder()
                                .url(photoUrl)
                                .shop(shop)
                                .build();

                        shopPhotoRepository.save(shopPhoto);
                    });

            return true;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    public boolean saveShopBusinessHours(Long shopId, List<ShopRequestDto.BusinessHoursReqDTO> businessHours){

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();

            businessHours.stream()
                    .forEach(businessHour -> {
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
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    public List<String> getShopPhotoUrls(Shop shop) {
        List<ShopPhoto> shopPhotoList = shopPhotoRepository.findShopPhotosByShop(shop);

        List<String> photoUrls = shopPhotoList.stream()
                .map(ShopPhoto::getUrl)
                .collect(Collectors.toList());

        return photoUrls;
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

    private String getCurrentDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek().name();
    }

    private List<ReviewResponseDto.ReviewDto> getReviewListDto(Shop shop) {

        List<Review> reviewList = reviewRepository.findTop3ByShopOrderByVisitedAtDesc(shop);

        List<ReviewResponseDto.ReviewDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    List<ReviewImage> reviewImages = reviewImageRepository
                            .findAllByReview(review).orElseThrow(()-> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
                    return ReviewConverter.toReviewDto(review, reviewImages);
                })
                .collect(Collectors.toList());

        return reviewDtoList;
    }
    private List<UserHelpInfoResponseDto.UserHelpInfoDto> getUserHelpInfoListDtoTop2(Shop shop) {

        return userHelpInfoRepository.findAllByShop(shop)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_HELP_INFO_NOT_FOUND))
                .stream()
                .map(userHelpInfo->{
                    Long likeCount = likeUserHelpInfoRepository.countByUserHelpInfo(userHelpInfo);
                    return UserHelpInfoConverter.toUserHelpInfoDto(userHelpInfo,likeCount);
                })
                // likeCount를 기준으로 내림차순으로 정렬
                .sorted((info1, info2) -> Long.compare(info2.getLikeCount(), info1.getLikeCount()))
                .limit(2)
                .toList();
    }



    private void checkOpenNow(List<ShopResponseDto.SearchByNameDto> dtos) {
        dtos.forEach(
                dto -> {
                    if (dto != null) {
                        Optional.ofNullable(dto.getShopBusinessHour()).ifPresentOrElse(
                                shopBusinessHour -> dto.setOpenNow(isCurrentTimeWithinOpenHours(shopBusinessHour.getOpenHours(), shopBusinessHour.getCloseHours())),
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
                                shopBusinessHour -> dto.setOpenNow(isCurrentTimeWithinOpenHours(shopBusinessHour.getOpenHours(), shopBusinessHour.getCloseHours())),
                                () -> dto.setOpenNow(false)
                        );
                    }
                }
        );
    }
}
