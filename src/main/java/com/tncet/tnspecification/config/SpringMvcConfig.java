package com.tncet.tnspecification.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;




@Configuration
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer{
    @Value("${file.static}")
    private String staticPath;

    @Value("${file.absolute}")
    private String absolutePath;
    

    // 设置静态资源的路由和本地路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticPath + "**")
                .addResourceLocations("file:" + absolutePath);

    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(DataSize.ofGigabytes(10));
        factory.setMaxRequestSize(DataSize.ofGigabytes(10));
        return factory.createMultipartConfig();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper om = converter.getObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);// 配置 NULL&空 是否序列化
        // om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);//
        // json数据对象采用下划线命名法\
        // om.setTimeZone(TimeZone.getTimeZone("GMT+8"));//设置默认接收时区，若传进来的时间和存储时间不一致，调整该参数
        // 反序列化配置
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// json中含pojo不存在属性时是否失败报错,默认true

        // 序列化设置
        om.configure(SerializationFeature.INDENT_OUTPUT, true);// 格式化json输出
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);// 对象为空时是否报错，默认true
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);// 返回的java.util.date转换成timestamp

        om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);// 允许出现单引号

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        byteArrayHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converter.setSupportedMediaTypes(supportedMediaTypes);

        converters.add(0, byteArrayHttpMessageConverter);
        converters.add(1, converter);
    }
}
