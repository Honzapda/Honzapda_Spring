package Honzapda.Honzapda_server.user.controller;

import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserJoinDto request){
        try {
            UserResDto userResDto = userService.registerUser(request);
            return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> searchUser(@PathVariable(name = "userId") Long userId){
        try {
            UserResDto userResDto = userService.searchUser(userId);
            return new ResponseEntity<>(userResDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserJoinDto request, @PathVariable(name = "userId") Long userId){
        try {
            UserResDto userResDto = userService.updateUser(request, userId);
            return new ResponseEntity<>(userResDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
