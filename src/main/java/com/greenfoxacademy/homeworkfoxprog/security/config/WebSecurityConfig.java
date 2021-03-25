package com.greenfoxacademy.homeworkfoxprog.security.config;

import com.greenfoxacademy.homeworkfoxprog.security.jwt.JwtTokenFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenFilter jwtTokenFilter;

  public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {

    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user1").password("user1Pass").roles("user")
        .authorities("USER")
        .and().withUser("admin").password("adminPass").roles("admin")
        .authorities("ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable().authorizeRequests()
        .antMatchers("/api/auth/**").permitAll()
        //.antMatchers(HttpMethod.POST, "/fox/**").permitAll()
        //.antMatchers(HttpMethod.PATCH, "/users/updateRole/*").hasAuthority("admin")
        //.antMatchers(HttpMethod.PATCH, "/users/updatePassword").hasAnyAuthority("user", "admin")
        //.antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
