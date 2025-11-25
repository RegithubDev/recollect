package com.resustainability.recollect.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.JwtUtil;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.exception.BaseException;
import com.resustainability.recollect.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final ApplicationContext context;
    private final Logger log;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, ApplicationContext context) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.context = context;
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {
        final long started = System.currentTimeMillis();
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String subject = null;
        String token = null;
        String rol = null;
        Long uat = null;

        try {
            if (StringUtils.isNotBlank(authorizationHeader) && jwtUtil.isBearer(authorizationHeader)) {
                token = jwtUtil.extractToken(authorizationHeader);
                uat = jwtUtil.extractUAT(token);
                rol = jwtUtil.extractROL(token);
                subject = jwtUtil.extractSubject(token);
            }

            if (StringUtils.isNotBlank(subject) && StringUtils.isNotBlank(rol) && null == SecurityContextHolder.getContext().getAuthentication()) {
                final UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserAndValidateUAT(subject, rol, uat);
                if (jwtUtil.validateToken(token, userDetails)) {
                    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            if (ex instanceof BaseException exception && !exception.isEmptyBody()) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                final String jsonResponse = objectMapper.writeValueAsString(new APIResponse<>(null, null, ex.getMessage()));
                response.getWriter().write(jsonResponse);
            }
        } finally {
            log(request, response, subject, System.currentTimeMillis() - started);
        }
    }

    private void log(HttpServletRequest request, HttpServletResponse response, String subject, long processingTime) {
        String remoteIp = request.getHeader(Default.HEADER_X_FORWARDED_FOR);
        String remoteHost = request.getHeader(HttpHeaders.ORIGIN);

        log.info(
                "{} | {}ms | {} | {} | {} | {} | {}",
                response.getStatus(),
                processingTime,
                request.getMethod(),
                request.getRequestURI(),
                StringUtils.isBlank(subject) ? "unknown" : subject,
                StringUtils.isBlank(remoteIp) ? request.getRemoteAddr() : remoteIp,
                StringUtils.isBlank(remoteHost) ? request.getRemoteHost() : remoteHost
        );
    }
}