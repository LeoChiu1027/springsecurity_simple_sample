package com.armi.sample.security;

import com.armi.sample.jpa.entity.Account;
import com.armi.sample.jpa.entity.AccountSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component("customUserDetailsContextMapper")
public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Autowired
    ProjectionFactory projectionFactory;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations dirContextOperations, String s, Collection<? extends GrantedAuthority> collection) {
        Account acc = new Account();
        acc.setId(s);
        acc.setPassword("empty");
        UserDetails userDetails = (UserDetails)new CustomUser(acc.getId(),
                acc.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), projectionFactory.createProjection(AccountSummary.class, acc));
        return userDetails;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException("LdapUserDetailsMapper only supports reading from a context. Pleaseuse a subclass if mapUserToContext() is required.");
    }
}
