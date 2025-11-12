package com.gpt.Config;

import com.gpt.Common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-04  12:16
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    // 设置静态资源请求
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    // 扩展MVC的纤细转换器
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("------------扩展自己的转换器----------");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        // 设置对象转换器，使用Jackson将java对象转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 将上面的消息转换器追加到MVC框架中的转换器容器
        converters.add(0,messageConverter);
    }
}
