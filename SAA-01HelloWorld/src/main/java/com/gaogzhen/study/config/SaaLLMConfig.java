package com.gaogzhen.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
//import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaogzhen
 * @create 2025-12-30
 */
@Configuration
public class SaaLLMConfig
{

    /**
     * 方式1:${}
     * 持有yml文件配置：spring.ai.dashscope.api-key=${aliQwen-api}
     */
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Value("${spring.ai.dashscope.base-url}")
    private String baseUrl;

    @Bean
    public DashScopeApi dashScopeApi()
    {
        return DashScopeApi.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }



    /**
     * 方式2:System.getenv("环境变量")
     * 持有yml文件配置：spring.ai.dashscope.api-key=${aliQwen-api}
     * @return
     */
//    @Bean
//    public DashScopeApi dashScopeApi()
//    {
//        return DashScopeApi.builder()
//                .apiKey(System.getenv("aliQwen-api"))
//            .build();
//    }

}
