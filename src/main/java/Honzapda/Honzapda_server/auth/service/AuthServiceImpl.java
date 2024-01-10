package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.auth.apple.AppleAuthClient;
import Honzapda.Honzapda_server.auth.apple.AppleIdTokenPayload;
import Honzapda.Honzapda_server.auth.apple.AppleProperties;
import Honzapda.Honzapda_server.auth.apple.AppleSocialTokenInfoResponse;
import Honzapda.Honzapda_server.auth.apple.common.TokenDecoder;
import Honzapda.Honzapda_server.user.data.dto.UserJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserLoginDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.UserRepository;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AppleAuthClient appleAuthClient;
    @Autowired
    private final AppleProperties appleProperties;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, AppleAuthClient appleAuthClient, AppleProperties appleProperties, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.appleAuthClient = appleAuthClient;
        this.appleProperties = appleProperties;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public ResponseEntity<?> appleLogin(String authorizationCode) {

        AppleSocialTokenInfoResponse appleToken = appleAuthClient.findAppleToken(
                appleProperties.getClientId(),
                generateClientSecret(),
                appleProperties.getGrantType(),
                authorizationCode
        );
        AppleIdTokenPayload appleIdTokenPayload = TokenDecoder.decodePayload(appleToken.getIdToken(), AppleIdTokenPayload.class);

        Optional<User> user = userRepository.findByEmail(appleIdTokenPayload.getEmail());

        if(user.isPresent()){
            return new ResponseEntity<>(UserResDto.toDTO(user.get()),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(appleToken,HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public UserResDto join(UserJoinDto userJoinDto) {

        // 중복 체크 하기
        User user = new User();
        user.setName(userJoinDto.getName());

        if(userJoinDto.getSocialToken()==null){
            // 일반 회원
            userRepository.findByEmail(userJoinDto.getEmail()).ifPresent(u->{
                throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");
            });
            user.setEmail(userJoinDto.getEmail());
            user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
            user.setSignUpType(User.SignUpType.LOCAL);
        }
        else{
            // 애플 회원
            AppleIdTokenPayload appleIdTokenPayload = TokenDecoder.decodePayload(userJoinDto.getSocialToken().getIdToken(), AppleIdTokenPayload.class);
            userRepository.findByEmail(appleIdTokenPayload.getEmail()).ifPresent(u->{
                throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");
            });
            user.setEmail(appleIdTokenPayload.getEmail());
            user.setSocialToken(userJoinDto.getSocialToken().getRefreshToken());
            user.setSignUpType(User.SignUpType.APPLE);
        }
        userRepository.save(user);
        return UserResDto.toDTO(user);
    }

    @Override
    public UserResDto login(UserLoginDto userLoginDto) {

        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(()->new UsernameNotFoundException("유저 정보 없음"));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        return UserResDto.toDTO(user);
    }

    @Override
    public void revoke(UserResDto userResDto) {

        User user = userRepository.findById(userResDto.getId()).orElseThrow(()-> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        System.out.println(user.getId());
        if(user.getSignUpType()== User.SignUpType.APPLE){
            String refreshToken = user.getSocialToken();
            if (refreshToken != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                appleAuthClient.revokeToken(
                        appleProperties.getClientId(),
                        generateClientSecret(),
                        refreshToken,
                        "refresh_token"
                );
            }

        }
        userRepository.deleteById(user.getId());


    }



    private String generateClientSecret() {

        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, appleProperties.getKeyId())
                .setIssuer(appleProperties.getTeamId())
                .setAudience(appleProperties.getAudience())
                .setSubject(appleProperties.getClientId())
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(appleProperties.getPrivateKey());

            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }

}
