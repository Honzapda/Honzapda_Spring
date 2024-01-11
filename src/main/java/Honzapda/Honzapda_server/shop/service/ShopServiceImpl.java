package Honzapda.Honzapda_server.shop.service;

import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.ShopRepository;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;

    private final ShopRepository shopRepository;

    public ShopResponseDto.searchDto registerShop(ShopRequestDto.registerDto request){

        Optional<User> user = userRepository.findById(request.getUserId());

        if(user.isPresent()){
            Shop shop = ShopConverter.toShop(request, user.get());
            shopRepository.save(shop);
            return ShopConverter.toShopResponse(shop);
        } else{
            // 에러
            return null;
        }
    }

    public ShopResponseDto.searchDto findShop(Long shopId){
        Optional<Shop> shop = shopRepository.findById(shopId);

        if (shop.isPresent()) {
            return ShopConverter.toShopResponse(shop.get());
        } else{
            return null;
        }
    }
}
