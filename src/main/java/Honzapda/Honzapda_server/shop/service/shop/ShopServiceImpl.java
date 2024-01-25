package Honzapda.Honzapda_server.shop.service.shop;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.review.data.ReviewConverter;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewImageRepository;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.data.entity.ShopPhoto;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopBusinessHourRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopPhotoRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    private final ReviewRepository reviewRepository;

    private final ShopPhotoRepository shopPhotoRepository;

    private final ReviewImageRepository reviewImageRepository;

    private final ShopBusinessHourRepository shopBusinessHourRepository;

    @Transactional
    public ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request){

        Shop shop = ShopConverter.toShop(request);
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

            ShopResponseDto.SearchDto resultDto = ShopConverter.toShopResponse(shop);
            resultDto.setRating(getRating(shopId));
            resultDto.setOpenNow(getOpenNow(businessHours));
            resultDto.setPhotoUrls(photoUrls);
            resultDto.setBusinessHours(businessHoursResDTOS);
            resultDto.setReviewList(reviewDtos);

            return resultDto;
        } else {
            throw new NoSuchElementException("해당 가게가 존재하지 않습니다.");
        }
    }

    @Override
    public Map<Long, ShopResponseDto.SearchDto> findShopsByShopIds(List<Long> mysqlIds) {
        List<Shop> shops = shopRepository.findByIdIn(mysqlIds);
        List<ShopResponseDto.SearchDto> searchDtos = shops.stream()
                .map(ShopConverter::toShopResponse)
                .toList();
        searchDtos.forEach(dto -> dto.setRating(getRating(dto.getShopId())));
        return ShopConverter.toShopResponseMap(searchDtos);
    }

    @Override
    public Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByReview(ShopRequestDto.SearchDto request, Pageable pageable) {
        return shopRepository.findByShopNameContainingOrderByReviewCountDesc(request.getKeyword(), pageable)
                .map(ShopConverter::toShopResponse);
    }

    @Override
    public Slice<ShopResponseDto.SearchDto> searchShopByShopNameContainingSortByBookmark(ShopRequestDto.SearchDto request, Pageable pageable) {
        return shopRepository.findByShopNameContainingOrderByBookmarkCountDesc(request.getKeyword(), pageable)
                .map(ShopConverter::toShopResponse);
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

        List<Review> reviewList = reviewRepository.findTop3ByShopOrderByCreatedAtDesc(shop);

        List<ReviewResponseDto.ReviewDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    List<ReviewImage> reviewImages = reviewImageRepository
                            .findAllByReview(review).orElseThrow(()-> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
                    return ReviewConverter.toReviewDto(review, reviewImages);
                })
                .collect(Collectors.toList());

        return reviewDtoList;
    }
}
