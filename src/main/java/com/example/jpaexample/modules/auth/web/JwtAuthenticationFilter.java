package com.example.jpaexample.modules.auth.web;

import com.example.jpaexample.modules.auth.application.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT인증
 * > Password필터 바로 직전 수행되어 스프링시큐리트가 처리할 수 있도록 JWT토큰을 파싱해줌)
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            if(jwt!=null && !"".equals(jwt) && JwtTokenProvider.validateTokne(jwt)){
                String userId = JwtTokenProvider.getUserIdFromJWT(jwt);

                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                if(jwt==null || "".equals(jwt)){
                    request.setAttribute("unauthorization", "401 인증키 없음.");
                }

                if(JwtTokenProvider.validateTokne(jwt)){
                    request.setAttribute("unauthorization", "401-001 인증키 만료");
                }
            }
        }catch(Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        String prefix = "Bearer ";
        if(bearerToken!=null && !"".equals(bearerToken)
                && StringUtils.startsWithIgnoreCase(bearerToken, prefix)){
            return bearerToken.substring(prefix.length());
        }
        return null;
    }
}
