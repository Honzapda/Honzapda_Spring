package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.UserConverter;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PreferRepository preferRepository;

    private final UserPreferRepository userPreferRepository;

    public UserResDto registerUser(UserJoinDto request){
        User user = UserConverter.toUser(request);

        userRepository.save(user);

        return UserConverter.toUserResponse(user);
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

    public UserResDto updateUser(UserJoinDto request, Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(request.getName());

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
