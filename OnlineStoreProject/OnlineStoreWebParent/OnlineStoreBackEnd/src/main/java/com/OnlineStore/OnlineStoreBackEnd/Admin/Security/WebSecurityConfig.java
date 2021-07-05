package com.OnlineStore.OnlineStoreBackEnd.Admin.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
        return new OnlineStoreUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/users/**").hasAuthority("Admin")
                    .antMatchers("/settings/**").hasAuthority("Admin")
                    .antMatchers("/categories/**").hasAnyAuthority( "Admin", "Editor")
                    .antMatchers("/shipping/**").hasAnyAuthority( "Admin", "Sales", "Shipping")
                    .antMatchers("/orders/**").hasAnyAuthority("Admin", "Sales", "Shipping")
                    .antMatchers("/products/**").hasAnyAuthority("Admin","Sales", "Shipping")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                        .and()
                        .logout()
                        .permitAll()
            ;
    }

    @Override
    public void configure(WebSecurity web) throws  Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }






}
