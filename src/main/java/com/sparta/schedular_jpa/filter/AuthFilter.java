package com.sparta.schedular_jpa.filter;

import com.sparta.schedular_jpa.entity.User;
import com.sparta.schedular_jpa.jwt.JwtUtil;
import com.sparta.schedular_jpa.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String a = httpServletRequest.getHeader("Authorization");
        log.info(a);
        String url = httpServletRequest.getRequestURI();
        System.out.println(url);
        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/auth/sign-up") || url.startsWith("/api/auth/sign-in"))
        ) {
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getJwtFromHeader(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증

                // 토큰 검증
                if (!jwtUtil.validateToken(tokenValue)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                User user = userRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                // 1. USER와 ADMIN 권한에 대한 제한
                if(accessAllowed(url, user.getRole().toString(), user.getId())) {
                    request.setAttribute("user", user);
                    chain.doFilter(request, response); // 다음 Filter 로 이동
                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                }

            } else {
                throw new IllegalArgumentException("Not Found Token");
            }
        }
    }

    private boolean accessAllowed(String url, String role, Long userId) {
        if("ADMIN".equals(role)) {
            return true;
        }

        if("USER".equals(role)) {

            if(url.startsWith("/api/users/" + userId)) {
                return true;
            }
            if(url.startsWith("/api/users")) {
                return false;
            }
            return true;
        }
        return false;
    }

}
