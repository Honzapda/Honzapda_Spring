package Honzapda.Honzapda_server.review.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.review.data.ReviewConverter;
import Honzapda.Honzapda_server.review.data.ReviewImageConverter;
import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewImageRepository;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    private final UserService userService;

    @Transactional
    public ReviewResponseDto.ReviewDto registerReview(Long userId, Long shopId, ReviewRequestDto.ReviewRegisterDto requestDto) {

        User user = userService.getUser(userId);
        Shop shop = findShopById(shopId);

        // 리뷰 중복 방지 TODO: 데모데이까지 주석처리
        // validateDuplicate(user, shop);

        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .status(true)
                .score(requestDto.getScore())
                .body(requestDto.getBody())
                .visitedAt(requestDto.getVisitedAt())
                .build();

        Review savedReview = reviewRepository.save(review);

        //리뷰 이미지 있으면,
        if(!requestDto.getReviewUrls().isEmpty())
            return includeReviewImageDto(requestDto,savedReview);

        return ReviewConverter.toReviewDto(savedReview);
    }

    private ReviewResponseDto.ReviewDto includeReviewImageDto(ReviewRequestDto.ReviewRegisterDto requestDto, Review savedReview){

        requestDto.getReviewUrls().forEach(url -> {
                    ReviewImage reviewImage = ReviewImage.builder()
                            .url(url)
                            .review(savedReview)
                            .shop(savedReview.getShop())
                            .build();
                    reviewImageRepository.save(reviewImage);
                });

        List<ReviewImage> reviewImages = reviewImageRepository.findAllByReview(savedReview)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));

        return ReviewConverter.toReviewDto(savedReview,reviewImages);

    }
    private Shop findShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
    }

    private void validateDuplicate(User user, Shop shop) {
        reviewRepository.findByUserAndShop(user, shop)
                .ifPresent(review -> {
                    throw new GeneralException(ErrorStatus.REVIEW_ALREADY_EXIST);
                });
    }

    public ReviewResponseDto.ReviewListDto getReviewListDto(Long shopId, Pageable pageable) {
        // 어디 shop 리뷰인지 확인
        Shop findShop = findShopById(shopId);

        // 최신순으로 리뷰 페이징 조회
        Page<Review> allByShopOrderByCreatedAtDesc = reviewRepository.findAllByShopOrderByVisitedAtDesc(findShop, pageable);

        // 리뷰와 이미지를 매핑하여 reviewDtos에 저장
        List<ReviewResponseDto.ReviewDto> reviewDtos = allByShopOrderByCreatedAtDesc.getContent().stream()
                .map(review -> {
                    List<ReviewImage> reviewImages = reviewImageRepository
                            .findAllByReview(review).orElseThrow(()-> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
                    return ReviewConverter.toReviewDto(review, reviewImages);
                })
                .collect(Collectors.toList());

        return ReviewConverter.toReviewListDto(
                allByShopOrderByCreatedAtDesc, reviewDtos, pageable.getPageNumber());
    }

    public ReviewImageResponseDto.ImageListDto getReviewImageListDto(Long shopId, Pageable pageable){
        // 어디 shop 리뷰인지 확인
        Shop findShop = findShopById(shopId);
        // Slice
        Slice<ReviewImage> allByShopOrderByCreatedAtDesc =
                reviewImageRepository.findAllByShopOrderByCreatedAtDesc(findShop, pageable);

        return ReviewImageConverter.toImageListDto(allByShopOrderByCreatedAtDesc);
    }
}
