package ddobab.blank.config.auth;

import ddobab.blank.domain.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/api/vi/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                       .logoutSuccessUrl("/")
                .and();
        return http.build();
    }
}
