package Honzapda.Honzapda_server.shop.data;

import Honzapda.Honzapda_server.shop.data.dto.MapResponseDto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapConverter {

    public static Map<Long, MapResponseDto.HomeDto> toMapResponseHomeDtoMap(List<Long> mysqlIds, List<MapResponseDto.HomeDto> homeDtos) {
        return mysqlIds.stream()
                .flatMap(id -> homeDtos.stream().filter(dto -> dto.getId().equals(id)))
                .collect(Collectors.toMap(
                        MapResponseDto.HomeDto::getId,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}
