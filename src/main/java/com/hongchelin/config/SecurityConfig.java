
package com.hongchelin.config;

import com.hongchelin.dto.whatForSignupDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        whatForSignupDTO whatForSignupDTO = new whatForSignupDTO();
        ThreadLocal<Boolean> isForLogin = whatForSignupDTO.isForLogin();

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .formLogin(oauth2 -> oauth2
                        .loginPage("/api/login")
                        .defaultSuccessUrl("/api/login/success", true))

                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {

                            Boolean isforlogin = whatForSignupDTO.isForLogin().get();

                            if (isforlogin != null && isforlogin) {
                                response.sendRedirect("/api/login/success");
                            } else {
                                response.sendRedirect("/regisInDB");
                            }
                        })
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}