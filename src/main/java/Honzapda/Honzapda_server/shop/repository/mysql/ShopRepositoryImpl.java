package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.review.data.entity.QReview;
import Honzapda.Honzapda_server.shop.data.dto.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static Honzapda.Honzapda_server.review.data.entity.QReview.review;
import static Honzapda.Honzapda_server.shop.data.entity.QShop.shop;
import static Honzapda.Honzapda_server.shop.data.entity.QShopBusinessHour.*;
import static Honzapda.Honzapda_server.shop.data.entity.QShopPhoto.*;
import static Honzapda.Honzapda_server.shop.data.entity.QShopUserBookmark.shopUserBookmark;

@RequiredArgsConstructor
@Slf4j
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MapResponseDto.HomeDto> findByMysqlIdIn(List<Long> mysqlIds) {
        List<MapResponseDto.HomeDto> homeDtos = queryFactory
                .select(new QMapResponseDto_HomeDto(shop, review.score.avg().coalesce(0.0), review.count()))
                .from(shop)
                .leftJoin(review).on(review.shop.id.eq(shop.id))
                .where(shop.id.in(mysqlIds))
                .groupBy(shop)
                .fetch();

        String todayOfWeek = LocalDateTime.now().getDayOfWeek().name();

        List<ShopResponseDto.SearchByNameDto> searchByNameDtos = getSearchByNameDtos(mysqlIds, todayOfWeek);

        List<ShopResponseDto.SearchByNameDto> searchByNameDtosOrdered = orderByIds(searchByNameDtos, mysqlIds);

        for (int i = 0; i < mysqlIds.size(); i++) {
            homeDtos.get(i).setShopBusinessHour(searchByNameDtosOrdered.get(i).getShopBusinessHour());
            homeDtos.get(i).setPhotoUrls(searchByNameDtosOrdered.get(i).getPhotoUrls());
        }

        return homeDtos;
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> findByShopNameContaining(String keyword, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(shop.id)
                .from(shop)
                .where(shopNameContaining(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        String todayOfWeek = LocalDateTime.now().getDayOfWeek().name();

        List<ShopResponseDto.SearchByNameDto> searchByNameDtos = getSearchByNameDtos(ids, todayOfWeek);

        List<ShopResponseDto.SearchByNameDto> shops = orderByIds(searchByNameDtos, ids);

        Long totalCount = getTotalCount(keyword);

        return new SliceTotal<>(shops, pageable, ids.size() == pageable.getPageSize(), totalCount);
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByReviewCountDesc(String keyword, Pageable pageable) {
        List<Tuple> fetch = queryFactory
                .select(shop.id, review.count())
                .from(shop)
                .leftJoin(review).on(review.shop.id.eq(shop.id))
                .groupBy(shop)
                .where(shopNameContaining(keyword))
                .orderBy(review.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Long> ids = fetch.stream()
                .map(tuple -> tuple.get(0, Long.class))
                .toList();

        String todayOfWeek = LocalDateTime.now().getDayOfWeek().name();

        List<ShopResponseDto.SearchByNameDto> searchByNameDtos = getSearchByNameDtos(ids, todayOfWeek);

        List<ShopResponseDto.SearchByNameDto> shops = orderByIds(searchByNameDtos, ids);

        Long totalCount = getTotalCount(keyword);

        return new SliceTotal<>(shops, pageable, fetch.size() == pageable.getPageSize(), totalCount);
//        return new SliceImpl<>(shops, pageable, fetch.size() == pageable.getPageSize());
    }

    private Long getTotalCount(String keyword) {
        return queryFactory
                .select(shop.count())
                .from(shop)
                .where(shopNameContaining(keyword))
                .fetchOne();
    }

    @Override
    public Slice<ShopResponseDto.SearchByNameDto> findByShopNameContainingOrderByBookmarkCountDesc(String keyword, Pageable pageable) {
        List<Tuple> fetch = queryFactory
                .select(shop.id, shopUserBookmark.count())
                .from(shop)
                .leftJoin(shopUserBookmark).on(shopUserBookmark.shop.id.eq(shop.id))
                .groupBy(shop)
                .where(shopNameContaining(keyword))
                .orderBy(shopUserBookmark.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<Long> ids = fetch.stream()
                .map(tuple -> tuple.get(0, Long.class))
                .toList();

        String todayOfWeek = LocalDateTime.now().getDayOfWeek().name();

        List<ShopResponseDto.SearchByNameDto> searchByNameDtos = getSearchByNameDtos(ids, todayOfWeek);

        List<ShopResponseDto.SearchByNameDto> shops = orderByIds(searchByNameDtos, ids);

        Long totalCount = getTotalCount(keyword);

        return new SliceTotal<>(shops, pageable, fetch.size() == pageable.getPageSize(), totalCount);
//        return new SliceImpl<>(shops, pageable, fetch.size() == pageable.getPageSize());
    }

    @Override
    public List<ShopResponseDto.SearchByNameDto> findSearchByNameDtoByMysqlIds(List<Long> mysqlIds) {
        String todayOfWeek = LocalDateTime.now().getDayOfWeek().name();

        return getSearchByNameDtos(mysqlIds, todayOfWeek);
    }

    private BooleanExpression shopNameContaining(String keyword) {
        return shop.shopName.contains(keyword);
    }

    private List<ShopResponseDto.SearchByNameDto> getSearchByNameDtos(List<Long> mysqlIds, String todayOfWeek) {
        List<ShopResponseDto.SearchByNameDto> fetch = queryFactory
                .select(new QShopResponseDto_SearchByNameDto(shop, shopBusinessHour))
                .from(shop)
                .leftJoin(shopBusinessHour).on(shopBusinessHour.shop.id.eq(shop.id))
                .where(
                        shop.id.in(mysqlIds),
                        shopBusinessHour.dayOfWeek.equalsIgnoreCase(todayOfWeek).or(shopBusinessHour.dayOfWeek.isNull()),
                        shopBusinessHour.isOpen.eq(true).or(shopBusinessHour.isOpen.isNull())
                )
                .fetch();

        List<Tuple> fetch2 = queryFactory
                .select(shopPhoto.shop.id, shopPhoto.url)
                .from(shopPhoto)
                .where(shop.id.in(mysqlIds))
                .fetch();

        // fetch2 리스트를 shop_id를 키로 갖고 url 리스트를 값으로 갖는 맵으로 변환합니다.
        Map<Long, List<String>> photoUrlsByShopId = fetch2.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, Long.class),
                        Collectors.mapping(tuple -> tuple.get(1, String.class), Collectors.toList())
                ));

        // fetch의 각 요소에 대해, 해당 요소의 shop_id에 해당하는 url 리스트를 photoUrls에 설정합니다.
        fetch.forEach(dto -> dto.setPhotoUrls(photoUrlsByShopId.get(dto.getShopId())));

        return fetch;
    }

    private List<ShopResponseDto.SearchByNameDto> orderByIds(List<ShopResponseDto.SearchByNameDto> searchByNameDtos, List<Long> ids) {
        Map<Long, ShopResponseDto.SearchByNameDto> shopMap = searchByNameDtos.stream()
                .collect(Collectors.toMap(ShopResponseDto.SearchByNameDto::getShopId, Function.identity()));

        return ids.stream()
                .map(shopMap::get)
                .toList();
    }
}
