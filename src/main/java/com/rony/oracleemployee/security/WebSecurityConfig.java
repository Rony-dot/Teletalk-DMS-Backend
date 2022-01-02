package com.rony.oracleemployee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER", "ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest()
//                .hasRole("USER")
//                .and().formLogin();

        http
                .sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS) // We don't need to create any session
                // also we don't need to store any value in the session
                .and ()
                .authorizeRequests().antMatchers("/login", "/register").permitAll()
                .antMatchers("/employees/**","/customers/**").hasAnyRole("ADMIN","EDITOR","SUPER_ADMIN")
                .antMatchers("/demo/**").hasAnyRole("USER")
                .and()
                .authenticationProvider (authenticationProvider)
                .addFilterBefore (authFIlter (), AnonymousAuthenticationFilter.class) // Will handle authentication
                .authorizeRequests ()
                .anyRequest()
                .authenticated()
                .and()
                // Disable unnecessary spring security features
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .cors();
    }

    public AuthFIlter authFIlter() throws Exception {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/**"),
                new AntPathRequestMatcher ("/token/**")
        );
        AuthFIlter authFIlter = new AuthFIlter(orRequestMatcher);
        authFIlter.setAuthenticationManager(authenticationManager());
        return authFIlter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}