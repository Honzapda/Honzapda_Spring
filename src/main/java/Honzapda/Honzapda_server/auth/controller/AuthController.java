package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;

import Honzapda.Honzapda_server.apiPayload.code.status.SuccessStatus;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.user.data.dto.*;
import Honzapda.Honzapda_server.user.data.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/checkId")
    public ApiResult<Boolean>
    checkId(@RequestBody @Valid UserEmailDto request) {
        return ApiResult.onSuccess(true);
    }

    @PostMapping("/register")
    public ApiResult<UserResDto>
    register(@RequestBody @Valid UserJoinDto request) {
        User newUser = authService.registerUser(request);
        return ApiResult.onSuccess(SuccessStatus._CREATED, UserResDto.toDTO(newUser));
    }

    @PostMapping("/login")
    public ApiResult<UserResDto>
    login(@RequestBody @Valid UserLoginDto request) {
        User loginUser = authService.loginUser(request);
        return ApiResult.onSuccess(UserResDto.toDTO(loginUser));
    }

    @PostMapping("/findId")
    public ApiResult<String>
    findId(@RequestBody @Valid FindEmailDto request) {

        User findUser = authService.getUserByNickName(request.getName());
        String email = findUser.getEmail();
        String masking = "**";
        return ApiResult.onSuccess(
                email.substring(0, email.indexOf("@")-masking.length())+masking);
        // 이메일 @ 앞까지만 반환 + 뒤에 두자리는 masking 처림
    }

    @PostMapping("/findPassword")
    public ApiResult<String>
    findPassword(@RequestBody @Valid FindPwDto request) {

        return ApiResult.onSuccess(authService.patchUserPassword(request.getEmail()));
    }

    @PostMapping("/apple")
    public ResponseEntity<?> appleLogin(HttpServletRequest request, @RequestParam("code") String authorizationCode){
        try{
            ResponseEntity<?> responseEntity = authService.appleLogin(authorizationCode);
            if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
                UserResDto userResDto = (UserResDto) responseEntity.getBody();

                assert userResDto != null;

                HttpSession session = request.getSession(true);
                session.setAttribute("user", userResDto);
                session.setMaxInactiveInterval(60 * 30);
            }
            return responseEntity;
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
    @PostMapping("/register")
    public ResponseEntity<?> join(@RequestBody UserJoinDto userJoinDto){

        try{
            UserResDto userResDto = authService.join(userJoinDto);
            return new ResponseEntity<>(userResDto, HttpStatus.CREATED);

        }
        catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request,@RequestBody UserLoginDto userLoginDto){
        try {
            UserResDto userResDto = authService.login(userLoginDto);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userResDto);
            session.setMaxInactiveInterval(60 * 30);
            return new ResponseEntity<>(userResDto, HttpStatus.OK);
        }
        catch (UsernameNotFoundException | BadCredentialsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session)
    {
        try{
            session.invalidate();
            return new ResponseEntity<>("logout Success!", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<?> revoke(@SessionAttribute UserResDto user){

        try{
            authService.revoke(user);
            return new ResponseEntity<>("Revoke Success!", HttpStatus.OK);
        }
        catch (UsernameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Revoke Failed!", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
