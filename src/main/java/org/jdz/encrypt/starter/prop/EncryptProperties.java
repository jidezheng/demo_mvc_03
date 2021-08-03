package org.jdz.encrypt.starter.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: JiDZ
 * @Date: 2021/8/3 10:34
 * @Description 配置加密的 key
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {
    public static final String DEFAULT_KEY = "www.itboyhub.com";
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
