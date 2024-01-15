package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.apiPayload.ApiResult;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.user.data.dto.AppleJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created", content = @Content(schema = @Schema(implementation = UserResDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = AppleJoinDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> appleLogin(HttpServletRequest request){
        try{
            String authorizationCode = request.getParameter("code");
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = String.class)))
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = String.class)))
    })
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
