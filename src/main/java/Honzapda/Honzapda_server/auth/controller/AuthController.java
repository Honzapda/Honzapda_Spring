package Honzapda.Honzapda_server.auth.controller;

import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/apple")
    public ResponseEntity<?> appleLogin(HttpServletRequest request, @RequestParam String authorizationCode){
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> logout(HttpSession session)
    {
        try{
            session.invalidate();
            return new ResponseEntity<>("logout Success!", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("logout Fail", HttpStatus.INTERNAL_SERVER_ERROR);
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
            // exception 종류에 따라 response 다르게 주기..!
            return new ResponseEntity<>("Revoke Failed!", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
