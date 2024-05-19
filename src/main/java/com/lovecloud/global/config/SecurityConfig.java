package com.lovecloud.global.config;

import com.lovecloud.global.crypto.BCryptCustomPasswordEncoder;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private static final String[] PUBLIC_ENDPOINTS = {
//            "/**",
//    };


    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new BCryptCustomPasswordEncoder();
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() { //정적 리소스 무시
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureHttpSecurity(httpSecurity);
        return httpSecurity.build();
    }

    private void configureHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .cors(withDefaults())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
//                        .anyRequest().authenticated());

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());  // 모든 요청을 허용

    }

}
