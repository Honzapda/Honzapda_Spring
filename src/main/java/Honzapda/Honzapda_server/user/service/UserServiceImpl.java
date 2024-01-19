package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.repository.ShopRepository;
import Honzapda.Honzapda_server.user.data.UserConverter;

import Honzapda.Honzapda_server.user.data.dto.LikeResDto;

import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.LikeRepository;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferResDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.Prefer;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.UserPrefer;
import Honzapda.Honzapda_server.user.repository.PreferRepository;
import Honzapda.Honzapda_server.user.repository.UserPreferRepository;

import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder encoder;
    private final PreferRepository preferRepository;
    private final UserPreferRepository userPreferRepository;


    @Override
    public boolean isEMail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isNickName(String name) {
        return userRepository.existsByName(name);
    }

    public UserResDto searchUser(Long userId){

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserConverter.toUserResponse(user);
        } else{
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
        }
    }

    public UserResDto updateUser(UserJoinDto userJoinDto, Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userJoinDto.getName());

            User savedUser = userRepository.save(user);
            return UserConverter.toUserResponse(savedUser);
        } else{
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
        }
    }

    public boolean registerUserPrefer(Long userId, List<String> preferNameList){

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            List<Prefer> preferList = toPreferList(preferNameList);

            saveUserPrefer(preferList, user);

            return true;

        } else{
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
        }
    }

    public UserPreferResDto searchUserPrefer(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){

            Set<UserPrefer> userPreferList = user.get().getUserPrefers();
            List<String> preferNameList = getPreferNameListByUserPreferList(userPreferList);
            return UserConverter.toUserPreferResponse(preferNameList);

        } else{
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
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


    public boolean updateUserPrefer(Long userId, List<String> preferNameList) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.getUserPrefers().clear();

            List<Prefer> preferList = toPreferList(preferNameList);
            saveUserPrefer(preferList, user);

            return true;

        } else{
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");
        }
    }

    private List<Prefer> toPreferList(List<String> preferNameList){
        return preferNameList.stream()
                .map(preferName -> preferRepository.findByPreferName(preferName)
                        .orElseGet(() -> {
                            Prefer newPrefer = new Prefer();
                            newPrefer.setPreferName(preferName);
                            return preferRepository.save(newPrefer);
                        }))
                .collect(Collectors.toList());
    }

    private void saveUserPrefer(List<Prefer> preferList, User user) {
        preferList.forEach(
                prefer -> {
                    UserPrefer userPrefer = UserPrefer.builder()
                            .user(user)
                            .prefer(prefer)
                            .build();
                    userPreferRepository.save(userPrefer);
                }
        );
    }

    private List<String> getPreferNameListByUserPreferList(Set<UserPrefer> userPreferList){

        List<String> preferNameList = new ArrayList<>();

        for (UserPrefer userPrefer : userPreferList) {
            Prefer prefer = userPrefer.getPrefer();
            String preferName = prefer.getPreferName();
            preferNameList.add(preferName);
        }

        return preferNameList;
    }

}
