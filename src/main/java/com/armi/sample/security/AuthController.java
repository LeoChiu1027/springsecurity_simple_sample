package com.armi.sample.security;

import com.armi.sample.jpa.entity.AccountSummary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    @RequestMapping("/api/principal")
    public AccountSummary getPrincipal(Principal principal){
        System.out.println("Principal:"+principal);
        if(principal instanceof UsernamePasswordAuthenticationToken){
            return ((CustomUser)((UsernamePasswordAuthenticationToken)principal).getPrincipal()).getAccount();
        }
        return null;
    }
}
