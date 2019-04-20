package com.food.sas.config;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Created by ygdxd_admin at 2019-04-19 8:24 PM
 */
@Slf4j
@Component
public class SmsSender implements InitializingBean {

    private static final String VERSION = "2017-05-25";

    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String ACTION = "SendSms";

    private static final String OK = "OK";

    @Value("${sms.TemplateCode}")
    private String templateCode;

    @Value("${sms.SignName}")
    private String signName;

    @Value("${sms.accessKeyId}")
    private String keyId;

    @Value("${sms.accessSecret}")
    private String secret;

    private DefaultProfile profile;
    private IAcsClient client;

    public boolean send(String phone, String code) {
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);
        request.setAction(ACTION);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error("send failure phone:" + phone + "  code:" + code + " reason:" + e.getMessage());
            return false;
        }
        System.out.println(response.getData());
        SmsResult result = JSON.parseObject(response.getData(), SmsResult.class);
        return OK.equals(result.getCode());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        profile = DefaultProfile.getProfile("default", keyId, secret);
        client = new DefaultAcsClient(profile);
    }
}


@Data
class SmsResult {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("BizId")
    private String bizId;

    @JsonProperty("Code")
    private String code;

}
