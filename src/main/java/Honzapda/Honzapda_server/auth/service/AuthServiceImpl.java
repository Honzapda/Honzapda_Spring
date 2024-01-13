package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.handler.LoginHandler;
import Honzapda.Honzapda_server.auth.apple.AppleAuthClient;
import Honzapda.Honzapda_server.auth.apple.AppleIdTokenPayload;
import Honzapda.Honzapda_server.auth.apple.AppleProperties;
import Honzapda.Honzapda_server.auth.apple.AppleSocialTokenInfoResponse;
import Honzapda.Honzapda_server.auth.apple.common.TokenDecoder;
import Honzapda.Honzapda_server.auth.data.AuthConverter;
import Honzapda.Honzapda_server.auth.data.dto.AuthRequestDto;
import Honzapda.Honzapda_server.auth.data.dto.AuthResponseDto;
import Honzapda.Honzapda_server.auth.repository.AuthRepository;
import Honzapda.Honzapda_server.user.data.dto.AppleJoinDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final AuthRepository authRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AppleAuthClient appleAuthClient;
    @Autowired
    private final AppleProperties appleProperties;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository authRepository, UserRepository userRepository, AppleAuthClient appleAuthClient, AppleProperties appleProperties, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.appleAuthClient = appleAuthClient;
        this.appleProperties = appleProperties;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean isEMail(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void registerUser(AuthRequestDto.Register request) {

        User newUser = AuthConverter.toUser(request, genName());
        authRepository.save(newUser);
    }

    @Override
    public String genName() {

        String name = null;
        List<String> adjective = Arrays.asList(
                "행복한", "슬픈", "게으른", "슬기로운", "수줍은",
                "그리운", "더러운", "섹시한", "배고픈", "배부른",
                "부자", "재벌", "웃고있는", "깨발랄한", "프로",
                "잔잔한", "아늑한", "시끄러운", "코딩", "깨끗한"
        );
        List<String> midName = Arrays.asList(
                "카페", "커피머신", "조명", "가격", "책상", "공간", "짐깅", "로딩",
                "웅이", "젬마", "제로", "휘리릭", "맥구", "체리", "이제");
        do {
            String number = (int) (Math.random() * 99) + 1 + "";
            Collections.shuffle(adjective);
            Collections.shuffle(midName);
            name = adjective.get(0) + midName.get(0) + number;

        } while (authRepository.existsByName(name));
        return name;
    }

    @Override
    public AuthResponseDto.Login loginUser(AuthRequestDto.Login request) {

        User getUser = authRepository.findByEmail(request.getEmail())
                .filter(find -> find.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new LoginHandler(ErrorStatus.LOGIN_NOT_MATCH));

        return AuthResponseDto.Login.builder()
                .memberId(getUser.getId())
                .build();
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

        if (user.isPresent()) {
            return new ResponseEntity<>(UserResDto.toDTO(user.get()), HttpStatus.OK);
        } else {
            AppleJoinDto appleJoinDto = AppleJoinDto.toDTO(appleIdTokenPayload.getEmail(), appleToken.getRefreshToken());
            return new ResponseEntity<>(appleJoinDto, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public UserResDto join(UserJoinDto userJoinDto) {

        // 중복 체크 하기
        User user = new User();
        user.setName(userJoinDto.getName());
        userRepository.findByEmail(userJoinDto.getEmail()).ifPresent(u -> {
            throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");
        });
        user.setEmail(userJoinDto.getEmail());

        if (userJoinDto.getSocialToken() == null) {
            // 일반 회원
            user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
            user.setSignUpType(User.SignUpType.LOCAL);
        } else {
            // 애플 회원
            user.setSocialToken(userJoinDto.getSocialToken());
            user.setSignUpType(User.SignUpType.APPLE);
        }
        userRepository.save(user);
        return UserResDto.toDTO(user);
    }

    @Override
    public UserResDto login(UserLoginDto userLoginDto) {

        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("유저 정보 없음"));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        return UserResDto.toDTO(user);
    }

    @Override
    public void revoke(UserResDto userResDto) {

        User user = userRepository.findById(userResDto.getId()).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        System.out.println(user.getId());
        if (user.getSignUpType() == User.SignUpType.APPLE) {
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
        private String generateClientSecret () {

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

        private PrivateKey getPrivateKey(){

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

