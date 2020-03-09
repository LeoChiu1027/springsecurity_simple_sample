package com.armi.sample.security;

import com.armi.sample.jpa.entity.Account;
import com.armi.sample.jpa.entity.AccountSummary;
import com.armi.sample.jpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DefaultUserDetailService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProjectionFactory projectionFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> optionalAccount = accountRepository.findById(username);
        if(optionalAccount.isPresent()){
            Account acc = optionalAccount.get();
            UserDetails userDetails = (UserDetails)new CustomUser(acc.getId(),
                    acc.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), projectionFactory.createProjection(AccountSummary.class, acc));
            return userDetails;
        }
        return null;
    }
}
