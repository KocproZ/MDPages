package ovh.kocproz.mdpages.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ovh.kocproz.mdpages.Util;
import ovh.kocproz.mdpages.data.model.UserModel;
import ovh.kocproz.mdpages.exception.UnauthorizedException;
import ovh.kocproz.mdpages.service.UserService;
import ovh.kocproz.mdpages.user.RefreshTokenRepository;
import ovh.kocproz.mdpages.user.entity.RefreshToken;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtTokenService {
    private UserService userService;
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey = "";
    private long validTime = 36000000;

    private long refreshTokenValidTime = 31L * 24L * 60L * 60L * 1000L;
    private long accessTokenValidTime = 15 * 60 * 1000;

    public JwtTokenService(UserService userService, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String issueRefreshToken(UserModel user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();
        String tokenId = Util.randomString(64);
        claims.put("tokenId", tokenId);

        RefreshToken refreshToken = new RefreshToken(0,
                user,
                tokenId,
                LocalDateTime.now(),
                LocalDateTime.now().plusNanos(refreshTokenValidTime * 1000));
        refreshTokenRepository.save(refreshToken);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String refreshRefreshToken(String oldRefreshToken) {
        Claims oldClaims = parse(oldRefreshToken);
        UserModel user = userService.getUser(oldClaims.getSubject());
        String oldTokenId = (String) oldClaims.get("tokenId");

        verifyRefreshToken((String) oldClaims.get("tokenId"), user);

        return issueRefreshToken(user);
    }

    public String issueAccessToken(String refreshToken) {
        Claims refreshTokenClaims = parse(refreshToken);
        UserModel user = userService.getUser(refreshTokenClaims.getSubject());
        verifyRefreshToken((String) refreshTokenClaims.get("tokenId"), user);


        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private void verifyRefreshToken(String tokenId, UserModel user) {
        refreshTokenRepository.findFirstByTokenAndUser(tokenId, user).orElseThrow(UnauthorizedException::new);
    }

    private Claims parse(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
