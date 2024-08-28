package com.sparta.schedular_jpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j(topic = "LoggingFilter")
@Component
@Order(1)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 전처리
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        System.out.println(url);
        log.info(url); // 어떤 요청인지에 대한 로그 생성

        filterChain.doFilter(request, response);

        // 후처리
        log.info(url); //
    }
}

