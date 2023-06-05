package com.tncet.tnspecification.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.time.Duration;
@Configuration
@EnableCaching
@Profile("prod")
public class RedisConfig extends CachingConfigurerSupport{
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;
    private Duration timeToLive = Duration.ofDays(1);
    @Resource
    private RedisSerializer<String> keySerializer;
    @Resource
    private RedisSerializer<String> valueSerializer;

    /**
     * 配置自定义redisTemplate
     * 
     * @return
     */
    @Bean
    RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);

        // GenericFastJsonRedisSerializer serializer = new
        // GenericFastJsonRedisSerializer();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager() {
        // 关键点，spring cache的注解使用的序列化都从这来，没有这个配置的话使用的jdk自己的序列化，实际上不影响使用，只是打印出来不适合人眼识别
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))// key序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))// value序列化方式
                .disableCachingNullValues()
                .entryTtl(timeToLive);// 缓存过期时间

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(config)
                .transactionAware();

        return builder.build();
    }

    @Bean
    public static RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public static RedisSerializer<Object> valueSerializer() {
        return new GenericFastJsonRedisSerializer();
    }
}
