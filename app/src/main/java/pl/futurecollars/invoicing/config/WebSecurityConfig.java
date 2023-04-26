package pl.futurecollars.invoicing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CorsFilter corsFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
    http.authorizeRequests().antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .addFilterBefore(corsFilter, ChannelProcessingFilter.class);


  }

}
