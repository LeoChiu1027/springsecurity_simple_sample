package com.armi.sample.security;

import com.armi.sample.jpa.entity.AccountSummary;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class CustomUser extends User {

    private AccountSummary account;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, AccountSummary account) {
        super(username, password, authorities);
        this.account = account;
    }
}
