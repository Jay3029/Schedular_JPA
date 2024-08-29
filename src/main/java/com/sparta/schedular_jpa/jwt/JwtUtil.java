package com.sparta.schedular_jpa.jwt;

import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.entity.UserRoleEnum;
import com.sparta.schedular_jpa.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // Header Key
    // HTTP 요청의 Header에서 JWT를 찾을 때 사용하는 HeaderKey
    // 일반적으로 Authorization 헤더에 JWT가 포함되어 있다
    public static final String AUTHORIZATION_HEADER = "Authorization";


    // 사용자 권한 값의 Key
    // JWT의 Payload에서 사용자 권한을 찾을때 사용하는 키
    public static final String AUTHORIZATION_KEY = "auth";


    // Token 식별자
    // JWT 토큰이 보통 Bearer로 시작하며, 이는 토큰 타입을 식별하기위한 일종의 접두사이다
    public static final String BEARER_PREFIX = "Bearer ";


    // 토큰 만료시간
    // 현재 JWT의 만료시간으로 1시간이 설정되어 있다
    // 기준이 Millisecond 단위라는 점 기억
    private final long ACCESS_TOKEN_TIME = 60 * 60 * 1000; // 60분
    // private final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000; // 7일


    // application.properties에서 jwt.secret.key 값을 받아와서 secretKey에 할당
    // 이 값은 Base64로 인코딩된 문자열이다
    @Value("${jwt.secret.key}")
    private String secretKey; // JWT의 Signature 생성에 사용되는 키 값
    private Key key; // JWT Signature에 사용되는 실제 Key 객체, 위 secretKey를 디코딩한 후에 이값을 이용해 HMAC_SHA 알고리즘용 키로 변환
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // JWT Signature에 사용할 알고리즘, 현재 HMAC_SHA256를 사용


    // Logger 객체를 사용해서 로그를 기록하는 클래스
    public static final Logger logger = LoggerFactory.getLogger("JWT 로그");


    // 초기화 메서드
    // @PostConstruct는 클래스가 인스턴스화된 후 초기화 작업을 수행하는 메서드에 붙이는 어노테이션
    // 저게 붙으면 Bean이 생성되고 의존성 주입이 완료된 후 자동으로 호출된다
    // 호출할 때마다 새로 생성하는 것을 방지하기 위해서 사용한다
    // 해당 메서드에서는 secretKey를 Base64로 디코딩한 후에 HMAC-SHA 키로 변환하여 Key 객체를 초기화하고 있다
    // 이후 해당 Key를 가지고 JWT의 서명을 생성하거나 검증하게 된다
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // Token 생성 메서드
    public String generateJwtToken(User user, UserRoleEnum role) {
        Long now = System.currentTimeMillis();

        String email = user.getEmail();

        return Jwts.builder()
                .setSubject(String.valueOf(email)) // 사용자 식별자값(ID)
                .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                .setExpiration(new Date(now + ACCESS_TOKEN_TIME)) // 만료 시간
                .setIssuedAt(new Date(now)) // 생성 시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact(); // 각 구성요소를 결합하여 JWT 문자열 생성

    }


    // JWT 헤더 에 저장
    // HttpServletResponse: 서버가 클라이언트에게 응답을 보낼때 필요한 정보를 설정하고 전송하는 역할
    // 응답 상태코드, 헤더, 응답 바디 등을 설정할 수 있음
    public void addJwtToHeader(String token, HttpServletResponse httpServletResponse) {
        // setHeader(String name, String value)를 사용해 Header 설정
        httpServletResponse.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + token);

    }

    // 헤더에서 JWT 토큰을 추출
    // HttpServletRequest: 클라이언트가 서버에게 요청을 보낼때 그 요청에 대한 정보를 얻을 수 있음
    public String getJwtFromHeader(HttpServletRequest httpServletRequest) {
        // getHeader(String name)를 사용해 특정 Header 값을 가져올 수 있음
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        // 가져온 값이 존재하는지, 그리고 그 값이 Bearer로 시작하는지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            // Bearer 부분만 잘라서 Token 반환
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null; // 유효한 토큰이 없으면 null 반환
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // JWT를 파싱하기 위한 빌더를 생성, JWT 서명 검증용 키 설정을 한 뒤 빌드
            // 그후에 parseClaimsJws()를 사용해서 JWT를 파싱하고 서명을 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }



    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public User getUserFromToken(HttpServletRequest request, UserRepository userRepository) {
        String token = new JwtUtil().getJwtFromHeader(request);
        Claims claims = getUserInfoFromToken(token);
        User user = userRepository.findByEmail(claims.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User")
        );
        return user;
    }
    public String getEmailFromToken(HttpServletRequest request) {
        String token = new JwtUtil().getJwtFromHeader(request);
        Claims claims = getUserInfoFromToken(token);
        return claims.getSubject();
    }

}
