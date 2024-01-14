package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

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
}
