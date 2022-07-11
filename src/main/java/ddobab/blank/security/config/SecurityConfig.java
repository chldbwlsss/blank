package ddobab.blank.security.config;

import ddobab.blank.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig{

    @Value("${const.url.front_server}")
    private String frontServerUrl;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/search/question").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/question/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/user/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/answer").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .redirectionEndpoint()
                            .baseUri("/api/login/oauth2/callback/*")
                        .and()
                        .defaultSuccessUrl(frontServerUrl)
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(frontServerUrl);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
