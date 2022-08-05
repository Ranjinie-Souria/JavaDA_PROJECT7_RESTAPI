package com.nnk.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nnk.springboot.service.MyUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }
    
    public PasswordEncoder getPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Override
 	public void configure(HttpSecurity http) throws Exception
 	{
    	
 		http.csrf().disable()
 				.authorizeRequests().antMatchers("/","/login/**","/css/**", "/img/**").permitAll()
 				.antMatchers("/user/**").hasAuthority("ADMIN")
 				.anyRequest().authenticated()
 				.and()
                .formLogin().defaultSuccessUrl("/default",true).permitAll()
                .and()
                .oauth2Login().defaultSuccessUrl("/default",true).permitAll()
				.and()
 					.logout()
 					.invalidateHttpSession(true).clearAuthentication(true)
 					.logoutUrl("/login")
 					.logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"));
 			
 			
 	}
    
}