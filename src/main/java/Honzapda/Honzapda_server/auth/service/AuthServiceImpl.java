package Honzapda.Honzapda_server.auth.service;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.handler.UserHandler;
import Honzapda.Honzapda_server.auth.apple.AppleAuthClient;
import Honzapda.Honzapda_server.auth.apple.AppleIdTokenPayload;
import Honzapda.Honzapda_server.auth.apple.AppleProperties;
import Honzapda.Honzapda_server.auth.apple.AppleSocialTokenInfoResponse;
import Honzapda.Honzapda_server.auth.apple.common.TokenDecoder;
import Honzapda.Honzapda_server.auth.util.PasswordGenerator;
import Honzapda.Honzapda_server.common.dto.SignUpType;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewImageRepository;
import Honzapda.Honzapda_server.review.repository.mysql.ReviewRepository;
import Honzapda.Honzapda_server.shop.repository.mysql.ShopUserBookmarkRepository;
import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.AppleJoinDto;
import Honzapda.Honzapda_server.user.data.dto.UserDto;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.repository.LikeRepository;
import Honzapda.Honzapda_server.user.repository.UserPreferRepository;
import Honzapda.Honzapda_server.user.repository.mysql.UserRepository;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoImageRepository;
import Honzapda.Honzapda_server.userHelpInfo.repository.UserHelpInfoRepository;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AppleAuthClient appleAuthClient;
    private final AppleProperties appleProperties;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final UserHelpInfoRepository userHelpInfoRepository;
    private final UserHelpInfoImageRepository userHelpInfoImageRepository;
    private final LikeRepository likeRepository;

    private final UserPreferRepository userPreferRepository;

    private final ShopUserBookmarkRepository shopUserBookmarkRepository;

    @Value("${honzapda.basic-image.url}")
    private String basicImageUrl;

    @Override
    @Transactional
    public User registerUser(UserDto.JoinDto request) {
        /*
        ** 이메일 중복체크는 어노테이션으로 이미 처리.
        ** Converter : 이메일, 이름, 비밀번호까지 처리 (Auth -> User 변경)
         */
        User newUser = UserConverter.toUser(request, passwordEncoder,basicImageUrl);

        if (request.getSocialToken() == null) {
            // 일반 회원
            newUser.setSignUpType(SignUpType.LOCAL);
        } else {
            // 애플 회원
            newUser.setSocialToken(request.getSocialToken());
            newUser.setSignUpType(SignUpType.APPLE);
        }

        return userRepository.save(newUser);
    }




    @Override
    public User loginUser(UserDto.LoginDto request) {

        User dbUser = getUserByEMail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(), dbUser.getPassword()))
            throw new UserHandler(ErrorStatus.PW_NOT_MATCH);

        return dbUser;
    }

    @Override
    public User getUserByEMail(String email) {

        return userRepository.findByEmail(email).orElseThrow(
                ()->new UserHandler(ErrorStatus.ID_NOT_EXIST));
    }

    @Override
    public void sendTempPasswordByEmail(String email) {

        User findUser = getUserByEMail(email);
        String tempPassword = PasswordGenerator.generateRandomPassword(8);
        emailService.sendTempPassword(email, tempPassword);
        findUser.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(findUser);

    }

    @Override
    public Object appleLogin(String authorizationCode) {

        AppleSocialTokenInfoResponse appleToken = appleAuthClient.findAppleToken(
                appleProperties.getClientId(),
                generateClientSecret(),
                appleProperties.getGrantType(),
                authorizationCode
        );
        AppleIdTokenPayload appleIdTokenPayload = TokenDecoder.decodePayload(appleToken.getIdToken(), AppleIdTokenPayload.class);

        Optional<User> user = userRepository.findByEmail(appleIdTokenPayload.getEmail());

        if (user.isPresent()) {
            return UserConverter.toUserInfo(user.get());
        } else {
            return AppleJoinDto.toDTO(appleIdTokenPayload.getEmail(), appleToken.getRefreshToken());
        }
    }

    @Override
    @Transactional
    public void revoke(UserResDto.InfoDto userResDto) {
        User user = userRepository.findById(userResDto.getId()).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        if (user.getSignUpType() == SignUpType.APPLE) {
            String refreshToken = user.getSocialToken();
            if (refreshToken != null) {
                //HttpHeaders headers = new HttpHeaders();
                //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                appleAuthClient.revokeToken(
                        appleProperties.getClientId(),
                        generateClientSecret(),
                        refreshToken,
                        "refresh_token"
                );
            }

        }

        deleteUserLikes(user);
        deleteUserHelpInfos(user);
        deleteUserReviews(user);
        deleteUserPrefers(user);
        deleteUserBookmarks(user);

        userRepository.delete(user);
    }

    private void deleteUserLikes(User user) {
        likeRepository.deleteAllByUser(user);
    }

    private void deleteUserHelpInfos(User user) {

        List<UserHelpInfo> userHelpInfoList = userHelpInfoRepository.findAllByUser(user);

        userHelpInfoList.stream()
                .forEach(userHelpInfo -> {
                    userHelpInfoImageRepository.deleteAllByUserHelpInfo(userHelpInfo);
                });

        userHelpInfoRepository.deleteAllByUser(user);
    }

    private void deleteUserReviews(User user) {

        List<Review> reviewList = reviewRepository.findAllByUser(user);

        reviewList.stream()
                .forEach(review -> {
                    reviewImageRepository.deleteAllByReview(review);
                });

        reviewRepository.deleteAllByUser(user);
    }

    private void deleteUserPrefers(User user) {
        userPreferRepository.deleteAllByUser(user);
    }

    private void deleteUserBookmarks(User user) {
        shopUserBookmarkRepository.deleteAllByUser(user);
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

