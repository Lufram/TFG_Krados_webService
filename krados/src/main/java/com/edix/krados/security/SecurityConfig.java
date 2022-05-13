package com.edix.krados.security;


import com.edix.krados.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter =  new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // PUBLIC
        http.authorizeRequests().antMatchers(GET, "/krados/login/**" ).permitAll();
        // PRODUCTS
        http.authorizeRequests().antMatchers(GET, "/product/**" ).hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/product/add/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/product/delete/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/product/update/**" ).hasAnyAuthority("ROLE_ADMIN");
        // USERS
        http.authorizeRequests().antMatchers(GET, "/krados/user/**" ).hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/krados/user/save/**" ).hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/krados/user/delete/**" ).hasAnyAuthority("ROLE_ADMIN");
        // ROLS

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
