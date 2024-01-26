package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapConverter {

    public static Map<Long, MapResponseDto.HomeDto> toMapResponseHomeDtoMap(List<Long> mysqlIds, List<MapResponseDto.HomeDto> homeDtos) {
        return mysqlIds.stream()
                .flatMap(id -> homeDtos.stream().filter(dto -> dto.getShopId().equals(id)))
                .collect(Collectors.toMap(
                        MapResponseDto.HomeDto::getShopId,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}
