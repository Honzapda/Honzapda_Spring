package Honzapda.Honzapda_server.shop.service.shop_congestion;

import Honzapda.Honzapda_server.shop.data.dto.ShopCongestionDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopAverageCongestion;
import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;
import Honzapda.Honzapda_server.shop.repository.mysql.AverageCongestionRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.DayCongestionRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCongestionServiceImpl implements ShopCongestionService {

    private final ShopRepository shopRepository;
    private final AverageCongestionRepository averageCongestionRepository;
    private final DayCongestionRepository dayCongestionRepository;

    @Override
    public ShopResponseDto.SearchDto registerCongestion(ShopRequestDto.RegisterDto registerDto, ShopResponseDto.SearchDto registeredShop) {

        Shop shop = shopRepository.findById(registeredShop.getShopId()).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));

        List<ShopCongestionDto.AverageCongestionDTO> averageCongestions = registerDto.getAverageCongestions();

        averageCongestions.forEach(averageCongestionDTO ->
                averageCongestionRepository.save(ShopCongestionDto.toEntity(averageCongestionDTO, shop)));

        List<ShopCongestionDto.DayCongestionDTO> dayCongestions = registerDto.getDayCongestions();

        dayCongestions.forEach(dayCongestionDTO ->
                dayCongestionRepository.save(ShopCongestionDto.toEntity(dayCongestionDTO, shop)));

        registeredShop.setCongestion(averageCongestions,dayCongestions);
        return registeredShop;
    }

    @Override
    public ShopResponseDto.SearchDto findShopCongestion(ShopResponseDto.SearchDto searchDto) {

        Shop shop = shopRepository.findById(searchDto.getShopId()).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));

        List<ShopAverageCongestion> averageCongestions = averageCongestionRepository.findAllByShopId(shop.getId());
        List<ShopCongestionDto.AverageCongestionDTO> averageCongestionDTOS = new ArrayList<>();
        averageCongestions.forEach(averageCongestion ->
                averageCongestionDTOS.add(ShopCongestionDto.toDTO(averageCongestion)
                ));

        List<ShopDayCongestion> dayCongestions = dayCongestionRepository.findAllByShopId(shop.getId());
        List<ShopCongestionDto.DayCongestionDTO> dayCongestionDTOS = new ArrayList<>();

        dayCongestions.forEach(dayCongestion ->
                dayCongestionDTOS.add(ShopCongestionDto.toDTO(dayCongestion)
                ));

        searchDto.setCongestion(averageCongestionDTOS,dayCongestionDTOS);

        return searchDto;

    }
}
