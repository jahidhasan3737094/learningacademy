package com.experment.learningacademy.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    public UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader=request.getHeader("Authorization");
        String username=null;
        String token=null;
        if(requestHeader !=null &&requestHeader.startsWith("Bearer")){
            token =requestHeader.substring(7);
            try {
                username=this.jwtToken.getUserNameFromToken(token);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            logger.error("invalid header information provided");

        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            ///invalid credentials
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            Boolean validToken=this.jwtToken.validateToken(token,userDetails);
            if(validToken){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                logger.error("validation error occurring..");
            }
        }
        filterChain.doFilter(request,response);
    }
}
