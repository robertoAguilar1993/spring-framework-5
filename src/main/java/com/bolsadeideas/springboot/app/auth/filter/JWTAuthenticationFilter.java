package com.bolsadeideas.springboot.app.auth.filter;

import com.bolsadeideas.springboot.app.auth.service.JWTService;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if(username != null && password !=null){
            logger.info("usename desde request parameter (form-data): " +username);
            logger.info("usename desde request parameter (form-data): " +password);
        }else{
            Usuario user=null;
            try {
                 user= new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
                 username = user.getUsername();
                 password = user.getPassword();
                logger.info("usename desde request parameter (row): " +username);
                logger.info("usename desde request parameter (row): " +password);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);


        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtService.create(authResult);
        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX+token);
        Map<String, Object> body = new HashMap<>();
        body.put("token",token);
        body.put("user",(User) authResult.getPrincipal());
        body.put("mensaje","Hola usuario, has iniciado session");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje","Error de authentificación username o password incorrecto");
        body.put("error",failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
