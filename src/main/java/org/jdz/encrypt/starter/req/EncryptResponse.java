package org.jdz.encrypt.starter.req;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jdz.encrypt.starter.annotation.Encrypt;
import org.jdz.encrypt.starter.prop.EncryptProperties;
import org.jdz.encrypt.starter.utils.AESUtils;
import org.jdz.encrypt.starter.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: JiDZ
 * @Date: 2021/8/3 10:50
 * @Description 接口加密
 */
@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<RespBean> {

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    EncryptProperties encryptProperties;
    /**
     * 判断什么样的接口需要加密
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    /**
     * 在数据响应之前执行
     */
    @Override
    public RespBean beforeBodyWrite(RespBean body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        byte[] bytes = encryptProperties.getKey().getBytes();
        try {
            if (body.getMsg() != null) {
                body.setMsg(AESUtils.encrypt(body.getMsg().getBytes(),bytes));
            }
            if (body.getObj() != null) {
                body.setObj(AESUtils.encrypt(om.writeValueAsBytes(body.getObj()),bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
