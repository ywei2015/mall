package com.nokia.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * RestTemplate配置
 * Created by YWW on 2019-04-25.
 */
@Configuration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public class RestTemplateConfig {

    private static final int HTTP_TIMEOUT_MS = 5000;


    private ClientHttpRequestFactory createFactory() {
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(HTTP_TIMEOUT_MS);
        f.setReadTimeout(HTTP_TIMEOUT_MS);
        return f;
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(this.createFactory());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //加入FastJson转换器 根据使用情况进行操作，此段注释，默认使用jackson
        //converterList.add(new FastJsonHttpMessageConverter4());
        return restTemplate;
    }

}
