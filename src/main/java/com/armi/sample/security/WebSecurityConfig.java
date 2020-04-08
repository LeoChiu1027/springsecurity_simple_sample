package com.armi.sample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("defaultUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    @Qualifier("customUserDetailsContextMapper")
    private UserDetailsContextMapper userDetailsContextMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginProcessingUrl("/api/login")
                .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler)
                .and().logout().logoutUrl("/api/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();

        http.addFilterAt(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);

        //jdbc authentication
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        //ldap authentication
//        auth
//            .ldapAuthentication()
//            .userDetailsContextMapper(userDetailsContextMapper)
//            .userDnPatterns("uid={0},ou=people")
//            .groupSearchBase("ou=groups")
//            .contextSource()
//            .managerDn("cn=Directory Manager").managerPassword("password")
//            .url("ldap://localhost:1389/dc=example,dc=com")
//            .and()
//            .passwordCompare()
//            .passwordEncoder(new LdapShaPasswordEncoder())
//            .passwordAttribute("userPassword");

    }

    JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() throws Exception {
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter();
        jwtAuthenticationProcessingFilter.setAuthenticationManager(this.authenticationManager());
        return jwtAuthenticationProcessingFilter;
    }
}
