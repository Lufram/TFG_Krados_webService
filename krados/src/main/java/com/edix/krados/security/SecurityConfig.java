package com.edix.krados.security;


import com.edix.krados.filter.CustomAuthenticationFilter;
import com.edix.krados.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter =  new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/krados/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // PUBLIC
        http.authorizeRequests().antMatchers(POST, "/krados/login/**" , "/krados/token/refresh/**").permitAll();
        // PRODUCTS
        http.authorizeRequests().antMatchers(GET, "/krados/product/**" ).hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/krados/product/add/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/krados/product/delete/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/krados/product/update/**" ).hasAnyAuthority("ROLE_ADMIN");
        // USERS
        http.authorizeRequests().antMatchers(GET, "/krados/user/**" ).hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/krados/user/save/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/krados/user/delete/**" ).hasAnyAuthority("ROLE_ADMIN");
        // ROLS
        http.authorizeRequests().antMatchers(GET, "/krados/role/**" ).hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/krados/role/save/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/krados/role/delete/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
