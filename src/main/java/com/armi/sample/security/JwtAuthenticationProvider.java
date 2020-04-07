package com.armi.sample.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private @Autowired
    HttpServletRequest request;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtParser jwtParser;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = (String) authentication.getPrincipal();
        try {
            if (StringUtils.isNotBlank(token)) {
                Jws<Claims> jws = jwtParser.parse(token);

                String username = (String)jws.getBody().get("username");

                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                JwtAuthenticationToken result = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            }
        } catch (IOException e) {
            throw new BadCredentialsException("JWT token is invalid [lost key file?]", e);
        } catch (NoSuchAlgorithmException e) {
            throw new BadCredentialsException("JWT token is invalid [algorithm error]", e);
        } catch (InvalidKeySpecException e) {
            //e.printStackTrace();
            throw new BadCredentialsException("JWT token is invalid [key spec error]", e);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }

}
