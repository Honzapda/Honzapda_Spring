package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import Honzapda.Honzapda_server.apiPayload.exception.handler.UserHandler;
import Honzapda.Honzapda_server.file.service.FileService;
import Honzapda.Honzapda_server.shop.data.ShopConverter;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopBusinessHourRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopRepository;
import Honzapda.Honzapda_server.shop.service.facade.ShopFacadeService;
import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.LikeResDto;
import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserPreferDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.Prefer;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.UserPrefer;
import Honzapda.Honzapda_server.user.repository.LikeRepository;
import Honzapda.Honzapda_server.user.repository.PreferRepository;
import Honzapda.Honzapda_server.user.repository.UserPreferRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
    private final ShopBusinessHourRepository shopBusinessHourRepository;
    private final FileService fileService;

    private final ShopFacadeService shopFacadeService;

    @Value("${honzapda.basic-image.url}")
    private String basicImageUrl;

    @Override
    public boolean isEMail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isNickName(String name) {
        return userRepository.existsByName(name);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public UserResDto.InfoDto patchPassword(UserDto.PatchUserPwDto request, Long userId) {

        User getUser = getUser(userId);
        getUser.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(getUser);

        return UserConverter.toUserInfo(getUser);
    }
    @Override
    public UserResDto.ProfileDto findUser(Long userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
        List<LikeData> likes = likeRepository.findAllByUser(user).orElseThrow(() -> new NoSuchElementException("찜한 가게가 없습니다."));

        return UserConverter.toUserProfile(user,likes);
    }
    @Override
    public UserResDto.InfoDto updateUser(UserDto.JoinDto userJoinDto, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        user.setName(userJoinDto.getName());
        User savedUser = userRepository.save(user);

        return UserConverter.toUserInfo(savedUser);
    }
    @Override
    public UserResDto.ProfileDto updateUserImage(MultipartFile image, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user가 존재하지 않습니다"));

        if (!user.getProfileImage().equals(basicImageUrl)) {
            String fileName = fileService.subStringUrl(user.getProfileImage());
            fileService.deleteObject(fileName);
        }
        String imageUrl = fileService.uploadObject(image);
        user.setProfileImage(imageUrl);
        userRepository.save(user);
        List<LikeData> likes = likeRepository.findAllByUser(user).orElseThrow(() -> new NoSuchElementException("찜한 가게가 없습니다."));

        return UserConverter.toUserProfile(user,likes);
    }
    @Override
    public UserPreferDto registerUserPrefer(Long userId, List<String> preferNameList){

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        List<Prefer> preferList = toPreferList(preferNameList);
        saveUserPrefer(preferList, user);

        return UserConverter.toUserPreferResponse(preferNameList);
    }
    @Override
    public UserPreferDto searchUserPrefer(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        Set<UserPrefer> userPreferList = user.getUserPrefers();
        List<String> preferNameList = getPreferNameListByUserPreferList(userPreferList);
        return UserConverter.toUserPreferResponse(preferNameList);
    }

    @Override
    public Slice<ShopResponseDto.likeDto> getLikeShops(Long id, ShopRequestDto.SearchDto request, Pageable pageable) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user가 존재하지 않습니다"));
        List<LikeData> likes = likeRepository.findAllByUser(user).orElseThrow(() -> new NoSuchElementException("찜한 가게가 없습니다."));

        return shopFacadeService.getLikeShopsSortBy(likes, request, pageable);
    }

    @Override
    public LikeResDto likeShop(Long shopId, Long userId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user가 존재하지 않습니다"));

        if (likeRepository.findByShopAndUser(shop, user).isPresent()) {
            throw new GeneralException(ErrorStatus.LIKE_ALREADY_EXIST);
        }
        else {
            LikeData likeData = LikeData.builder()
                    .shop(shop)
                    .user(user)
                    .build();

            likeRepository.save(likeData);
            return LikeResDto.toDTO(likeData);
        }

    }

    @Override
    @Transactional
    public LikeResDto deleteLikeShop(Long shopId, Long userId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("shop이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user가 존재하지 않습니다"));
        LikeData likeData = likeRepository.findByShopAndUser(shop, user).orElseThrow(() -> new GeneralException(ErrorStatus.LIKE_NOT_EXIST));

        likeRepository.deleteById(likeData.getId());

        return LikeResDto.toDTO(likeData);
    }

    @Override
    public boolean updateUserPrefer(Long userId, List<String> preferNameList) {

        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user가 존재하지 않습니다"));

        user.getUserPrefers().clear();

        List<Prefer> preferList = toPreferList(preferNameList);
        saveUserPrefer(preferList, user);
        return true;
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

    public List<ShopResponseDto.BusinessHoursResDTO> getShopBusinessHours(Shop shop) {
        List<ShopBusinessHour> businessHours = shopBusinessHourRepository.findShopBusinessHourByShop(shop);

        return businessHours.stream()
                .map(ShopConverter::toShopBusinessHourDto)
                .collect(Collectors.toList());
    }
}
