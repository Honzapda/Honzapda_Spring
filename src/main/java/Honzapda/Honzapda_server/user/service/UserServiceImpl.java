package Honzapda.Honzapda_server.user.service;

import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserRequestDto;
import Honzapda.Honzapda_server.user.data.dto.UserResponseDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


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
}
