package com.startsteps.ecommerceapi.user.security.jwt;
import com.startsteps.ecommerceapi.user.service.EcomUserAdapter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;
import java.security.Key;
import java.util.Date;

@Component
@Getter
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${JWT_EXPIRES_IN}")
    private long accesTokenValidity;
    @Value("${cookie}")
    private String cookie;
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, getCookie());
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(EcomUserAdapter userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(getCookie(), jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(getCookie(), null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accesTokenValidity))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }


}
