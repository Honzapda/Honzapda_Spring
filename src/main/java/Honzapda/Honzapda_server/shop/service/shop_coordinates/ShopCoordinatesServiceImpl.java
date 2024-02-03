package Honzapda.Honzapda_server.shop.service.shop_coordinates;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.shop.data.dto.MapRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import Honzapda.Honzapda_server.shop.repository.mongo.ShopCoordinatesRepository;
import Honzapda.Honzapda_server.shop.service.shop_coordinates.dto.ShopCoordinatesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCoordinatesServiceImpl implements ShopCoordinatesService {

    private final ShopCoordinatesRepository shopCoordinatesRepository;

    @Override
    public ShopCoordinates registerShopCoordinates(ShopCoordinatesDto request) {
        if (shopCoordinatesRepository.existsByMysqlId(request.getMysqlId())) {
            throw new GeneralException(ErrorStatus.SHOP_ALREADY_EXIST);
        }
        else{
            return shopCoordinatesRepository.save(request.toEntity());
        }
    }

    @Override
    public ShopCoordinates findShopCoordinates(Long shopId) {
        return shopCoordinatesRepository.findByMysqlId(shopId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SHOP_COORDINATES_NOT_FOUND));
    }

    @Override
    public List<ShopCoordinates> findShopsByLocation(MapRequestDto.LocationDto locationDto) {
        Point point = new Point(locationDto.getLongitude(), locationDto.getLatitude());
        Distance distance = new Distance(locationDto.getDistance(), Metrics.KILOMETERS);
        return shopCoordinatesRepository.findByLocationNear(point, distance);
    }

    @Override
    public Page<ShopCoordinates> findByShopNameContainingAndLocationNear(ShopRequestDto.SearchDto searchDto, Pageable pageable) {
        Point point = new Point(searchDto.getLongitude(), searchDto.getLatitude());
        Distance distance = new Distance(searchDto.getDistance(), Metrics.KILOMETERS);
        return shopCoordinatesRepository.findByShopNameContainingAndLocationNear(searchDto.getKeyword(), point, distance, pageable);
    }

    @Override
    public List<ShopCoordinates> findShopsCoordiates(List<Long> mysqlIds) {
        return shopCoordinatesRepository.findAllByMysqlIdIn(mysqlIds);
    }

    public Double getDistance(ShopRequestDto.SearchDto searchDto, Shop shop){
        Point point = new Point(searchDto.getLongitude(), searchDto.getLatitude());

        ShopCoordinates shopCoordinates = shopCoordinatesRepository.findByShopName(shop.getShopName());
        Point shopPoint;
        if (shopCoordinates == null) {
            shopPoint = new Point(0, 0);
        } else{
            shopPoint = shopCoordinates.getLocation();
        }

        double dX = point.getX() - shopPoint.getX();
        double dY = point.getY() - shopPoint.getX();
        return Math.sqrt((dX * dX) + (dY * dY));
    }
}
