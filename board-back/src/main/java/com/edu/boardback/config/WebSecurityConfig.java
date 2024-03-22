package com.edu.boardback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.edu.boardback.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        //HttpSecurity:스프링시큐리티 대부분설정을 담당 하는 객체
        //CORS란 “Cross-Origin Resource Sharing”의 약자. CORS는 프로토콜
        //서로 다른 origin일 시 리소스와 상호 작용하기 위해 클라이언트인 브라우저에서 실행되는 스크립트
        httpSecurity
            .cors(cors -> cors.configurationSource(corsConfigrationSource())
            )
            .csrf(CsrfConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .sessionManagement(sessionManagement->sessionManagement
                .sessionCreationPolicy(SessionCreateionPolicy.STATELESS)
            )
            .authorizeHttpRequests(request->request
                .requestMatchers("/","/api/v1/auth/**","/api/v1/search/**","/file/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/board/**","/api/v1/user/*").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            // .cors().and() // 기본 cors정책을 사용
            // .csrf().disable() //CSRF 보호를 비활성화
            // .httpBasic().disable() // Http basic Auth 기반으로 로그인 인증창이 뜸. disable 시에 인증창 뜨지 않음
            // .sessionManagement().sessionCreationPolicy(SessionCreateionPolicy.STATELESS).and()
        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigrationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}