package com.bytebandits.fintrackbackend;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CorsConfig extends OncePerRequestFilter {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

//        httpServletResponse.addHeader("Access-Control-Allow-Origin","http://127.0.0.1:5173");
//        httpServletResponse.addHeader("Access-Control-Allow-Origin","http://localhost:5173");
        httpServletResponse.addHeader("Access-Control-Allow-Origin","https://fin-track-frontend.vercel.app");
        httpServletResponse.addHeader("Access-Control-Allow-Methods","GET, POST, PUT, PATCH, DELETE, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token, location");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "content-type, location");
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

