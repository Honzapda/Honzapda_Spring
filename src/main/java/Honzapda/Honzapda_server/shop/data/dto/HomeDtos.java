package Honzapda.Honzapda_server.shop.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class HomeDtos {
    private List<MapResponseDto.HomeDto> homeDtos=new ArrayList<>();

    public HomeDtos(List<MapResponseDto.HomeDto> homeDtos) {
        this.homeDtos = homeDtos;
    }
}
