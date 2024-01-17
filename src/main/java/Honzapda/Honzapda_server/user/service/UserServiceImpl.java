package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.ShopRepository;
import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;
import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.LikeRepository;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ShopRepository shopRepository;

    @Override
    public boolean isEMail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isNickName(String name) {
        return userRepository.existsByName(name);
    }

    public UserResponseDto.searchDto searchUser(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserConverter.toUserResponse(user);
        } else{
            return null;
        }
    }

    public UserResponseDto.searchDto updateUser(UserRequestDto.updateDto request, Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(request.getName());

            User savedUser = userRepository.save(user);
            return UserConverter.toUserResponse(savedUser);
        } else{
            return null;
        }
    }
    @Override
    public List<ShopResponseDto.searchDto> getLikeShops(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user가 존재하지 않습니다"));
        List<LikeData> likes = user.getLikes();
        List<ShopResponseDto.searchDto> likeshops = new ArrayList<>();
        likes.forEach(likeData ->
                likeshops.add(ShopConverter.toShopResponse(likeData.getShop()))
        );
        return likeshops;
    }

    @Override
    public LikeResDto likeShop(Long shopId, Long userId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user가 존재하지 않습니다"));
        Optional<LikeData> byShopAndUser = likeRepository.findByShopAndUser(shop, user);
        if(byShopAndUser.isEmpty()){
            LikeData likeData = new LikeData();
            user.getLikes().add(likeData);
            System.out.println(user.getLikes().size());
            likeData.setShop(shop);
            likeData.setUser(user);
            likeRepository.save(likeData);
            return LikeResDto.toDTO(likeData);
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이미 찜을 눌렀습니다.");
        }
    }

    @Override
    public LikeResDto deleteLikeShop(Long shopId, Long userId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user가 존재하지 않습니다"));
        Optional<LikeData> byShopAndUser = likeRepository.findByShopAndUser(shop, user);
        if (byShopAndUser.isPresent()){
            user.getLikes().removeIf(data ->
                    data.getShop().equals(shop)
            );
            return LikeResDto.toDTO(byShopAndUser.get());
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "찜을 누른 적이 없습니다");
        }
    }


}
