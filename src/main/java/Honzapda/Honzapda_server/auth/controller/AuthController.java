package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.shop.data.dto.ShopRequestDto;
import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.shop.service.ShopService;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final ShopService shopService;

    /**
     * id 중복 검사 api
     * 실패 : 에러
     * 성공 : True
     */
    @PostMapping("/checkId")
    public ApiResult<Boolean>
    checkId(@RequestBody @Valid AuthRequestDto.GetEmail request) {
        return ApiResult.onSuccess(true);
    }
    /**
     * 회원가입 api
     * 실패 : 에러
     * 성공 : True
     */
//    @PostMapping("/register")
//    public ApiResult<Boolean>
//    register(@RequestBody @Valid AuthRequestDto.Register request) {
//        authService.registerUser(request);
//        return ApiResult.onSuccess(SuccessStatus._CREATED,true);
//    }
//    /**
//     * 로그인 api
//     * 실패 : 에러
//     * 성공 : True
//     */
//    @PostMapping("/login")
//    public ApiResult<AuthResponseDto.Login>
//    register(@RequestBody @Valid AuthRequestDto.Login request) {
//
//        return ApiResult.onSuccess(authService.loginUser(request));
//    }

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

    @PostMapping("/register/shop")
    public ResponseEntity<?> registerShop(
            @RequestBody @Valid ShopRequestDto.registerDto request)
    {
        try {
            ShopResponseDto.searchDto responseDto = shopService.registerShop(request);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
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
