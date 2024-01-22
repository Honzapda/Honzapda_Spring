package Honzapda.Honzapda_server.shop.service.facade;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.BookmarkConverter;
import Honzapda.Honzapda_server.shop.data.dto.*;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import Honzapda.Honzapda_server.shop.data.entity.ShopUserBookmark;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopUserBookmarkRepository;
import Honzapda.Honzapda_server.shop.service.shop.ShopService;
import Honzapda.Honzapda_server.shop.service.shop_coordinates.ShopCoordinatesService;
import Honzapda.Honzapda_server.shop.service.shop_coordinates.dto.ShopCoordinatesDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopFacadeService {

    private final ShopService shopService;
    private final ShopCoordinatesService shopCoordinatesService;
    private final ShopUserBookmarkRepository shopUserBookmarkRepository;

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public ShopResponseDto.SearchDto registerShop(ShopRequestDto.RegisterDto request) {
        // mysql에 상점 등록
        // 좌표 등록 없는 반환 객체
        ShopResponseDto.SearchDto registeredShop = shopService.registerShop(request);

        // mongoDB에 좌표 등록
        ShopCoordinatesDto coordinatesDto = ShopCoordinatesDto.of(registeredShop.getShopId(), request.getShopName(), request.getLatitude(), request.getLongitude());
        ShopCoordinates shopCoordinates = shopCoordinatesService.registerShopCoordinates(coordinatesDto);

        // 좌표 등록한 반환 객체
        registeredShop.addCoordinates(shopCoordinates);

        return registeredShop;
    }

    public ShopResponseDto.SearchDto findShop(Long shopId) {
        // 좌표 등록 없는 반환 객체
        ShopResponseDto.SearchDto searchDto = shopService.findShop(shopId);

        // 좌표 등록한 반환 객체
        searchDto.addCoordinates(shopCoordinatesService.findShopCoordinates(shopId));

        return searchDto;
    }

    public List<ShopResponseDto.SearchDto> findShopsByLocation(MapRequestDto.LocationDto locationDto) {
        List<ShopCoordinates> shopCoordinates = shopCoordinatesService.findShopsByLocation(locationDto);
        List<Long> mysqlIds = shopCoordinates.stream()
                .map(ShopCoordinates::getMysqlId)
                .toList();

        Map<Long, ShopResponseDto.SearchDto> shopMap = shopService.findShopsByShopIds(mysqlIds);

        shopCoordinates.forEach(coordinates -> {
            ShopResponseDto.SearchDto shop = shopMap.get(coordinates.getMysqlId());
            shop.addCoordinates(coordinates);
        });

        return shopMap.values().stream()
                .toList();
    }

    @Transactional
    public MapResponseDto.BookmarkResponseDto addBookmark(Long userId, Long shopId) {

        User user = findUserById(userId);
        Shop shop = findShopById(shopId);
        validateBookmarkAlreadyExist(user, shop);

        shopUserBookmarkRepository.save(BookmarkConverter.toBookmark(user, shop));

        return MapResponseDto.BookmarkResponseDto.builder()
                .userId(userId)
                .shopId(shopId)
                .build();
    }

    @Transactional
    public MapResponseDto.BookmarkResponseDto deleteBookmark(Long id, Long shopId) {

        User user = findUserById(id);
        Shop shop = findShopById(shopId);
        ShopUserBookmark bookmark = findBookmarkByUserAndShop(user, shop);

        shopUserBookmarkRepository.delete(bookmark);

        return MapResponseDto.BookmarkResponseDto.builder()
                .userId(id)
                .shopId(shopId)
                .build();
    }

    public Slice<ShopResponseDto.SearchDto> searchShop(UserResDto userResDto, ShopRequestDto.SearchDto request, Pageable pageable) {
        User user = findUserById(userResDto.getId());
        SortColumn sortColumn = request.getSortColumn();

        if (sortColumn == SortColumn.DISTANCE) {
            return searchSortByDistance(request, pageable);
        }

        if (sortColumn == SortColumn.REVIEW_COUNT) {
            return shopService.searchShopByShopNameContainingSortByReview(request, pageable);
        }

        if (sortColumn == SortColumn.BOOKMARK_COUNT) {
            return shopService.searchShopByShopNameContainingSortByBookmark(request, pageable);
        }

        if (sortColumn == SortColumn.RECOMMEND) {
            // TODO: 추천수 미구현
//            return shopService.searchShopByRecommend(user, request, pageable);
            return null;

        }

        // TODO: SortColumn.EMPTY 처리 -> 그냥 모든 결과 or 에러 ?
        return null;
    }

    private SliceImpl<ShopResponseDto.SearchDto> searchSortByDistance(ShopRequestDto.SearchDto request, Pageable pageable) {
        Slice<ShopCoordinates> shopCoordinates = shopCoordinatesService.findByShopNameContainingAndLocationNear(request, pageable);
        List<Long> mysqlIds = shopCoordinates.getContent().stream()
                .map(ShopCoordinates::getMysqlId)
                .toList();

        Map<Long, ShopResponseDto.SearchDto> shopMap = shopService.findShopsByShopIds(mysqlIds);

        shopCoordinates.getContent().forEach(coordinates -> {
            ShopResponseDto.SearchDto shop = shopMap.get(coordinates.getMysqlId());
            shop.addCoordinates(coordinates);
        });

        List<ShopResponseDto.SearchDto> searchDtos = shopMap.values().stream()
                .toList();
        return new SliceImpl<>(searchDtos, pageable, shopCoordinates.hasNext());
    }

    private User findUserById(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        // TODO: 2024-01-17 임시로 만들어둠
        return User.builder().id(userId).build();
    }

    private Shop findShopById(Long shopId) {
        return shopRepository.findById(shopId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_NOT_FOUND));
    }

    private void validateBookmarkAlreadyExist(User user, Shop shop) {
        shopUserBookmarkRepository.findByUserAndShop(user, shop)
                .ifPresent(bookmark -> {
                    throw new GeneralException(ErrorStatus.BOOKMARK_ALREADY_EXIST);
                });
    }

    private ShopUserBookmark findBookmarkByUserAndShop(User user, Shop shop) {
        return shopUserBookmarkRepository.findByUserAndShop(user, shop)
                .orElseThrow(() -> new GeneralException(ErrorStatus.BOOKMARK_NOT_FOUND));
    }
}