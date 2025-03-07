package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.properties.HwObsProperties;
import com.sky.utils.AliOssUtil;
import com.sky.utils.HwObsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean   // 容器中仅有一个
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建上传阿里云文件上传工具类 {}", aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

    @Bean
    @ConditionalOnMissingBean
    public HwObsUtil hwObsUtil(HwObsProperties hwObsProperties) {
        log.info("开始创建上传华为云文件上传工具类 {}", hwObsProperties);
        return new HwObsUtil(hwObsProperties.getEndpoint(),
                hwObsProperties.getAccessKeyId(),
                hwObsProperties.getAccessKeySecret(),
                hwObsProperties.getBucketName());
    }
}
