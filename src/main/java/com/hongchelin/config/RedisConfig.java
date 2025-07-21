package com.hongchelin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory(@Value("${spring.data.redis.host}")String host, @Value("${spring.data.redis.port}")int port) {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(@Value("${spring.data.redis.host}")String host, @Value("${spring.data.redis.port}")int port) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(host, port));
        return redisTemplate;
    }
}
