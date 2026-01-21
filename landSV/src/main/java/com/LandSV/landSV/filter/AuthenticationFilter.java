package com.LandSV.landSV.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.LandSV.landSV.entity.CustomizedUserDetails;
import com.LandSV.landSV.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);

            Claims claims = jwtService.extractClaims(token);

            CustomizedUserDetails userDetails = new CustomizedUserDetails(
                    claims.get("password", String.class),
                    claims.get("username", String.class),
                    claims.get("email", String.class),
                    claims.get("id", String.class),
                    claims.get("profileImage", String.class),
                    null
            );

            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                if(jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {sendError(response,HttpStatus.UNAUTHORIZED.value() ,"The session has expired");}
        catch (JwtException ex) {sendError(response,HttpStatus.UNAUTHORIZED.value() ,"The session is invalid");}
        catch (Exception ex) {sendError(response,HttpStatus.INTERNAL_SERVER_ERROR.value() ,"Unknown error");}
    }

    private static void sendError (HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        var error = Map.of("status", status, "message", message);
        new ObjectMapper().writeValue(response.getWriter(), error);
    }
}
