package Honzapda.Honzapda_server.review.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.review.data.ReviewConverter;
import Honzapda.Honzapda_server.review.data.dto.ReviewRequestDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public ReviewResponseDto.ReviewDto registerReview(Long userId, Long shopId, ReviewRequestDto.ReviewRegisterDto requestDto) {

        User user = User.builder().id(userId).build();
        Shop shop = findShopById(shopId);

        // 리뷰 중복 방지
        validateDuplicate(user, shop);

        Review review = Review.builder()
                .user(user)
                .shop(shop)
                .status(true)
                .score(requestDto.getScore())
                .body(requestDto.getBody())
                .build();
        Review savedReview = reviewRepository.save(review);

        return ReviewConverter.toReviewDto(savedReview);
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
}
