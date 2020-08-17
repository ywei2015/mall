package com.nokia.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户权限信息
 * @author yww
 */
@RestController
public class UserPermissionController {

    @Value("${fetch_user_permission_url}")
    private String fetch_permission_url;
    @Autowired
    private RestTemplate restTemplate;

    private final static Logger log = LoggerFactory.getLogger(UserPermissionController.class);

    /**
     * 获取用户权限
     * @param resourcesId
     * @return
     */
    @GetMapping("user/permission")
    public JSONObject fetchUserPermission(HttpServletRequest httpServletRequest, @RequestParam String resourcesId,
										  @RequestParam(required = false) String userId){
        JSONObject jsonObject = new JSONObject();
        String userId_ = (String) httpServletRequest.getSession().getAttribute("userId");
        log.info("obtain userId :{}",userId_);
        if (StringUtils.isNotBlank(userId_) && StringUtils.isNotBlank(resourcesId)){
            MultiValueMap<String, Object> postParams = new LinkedMultiValueMap<>();
            postParams.add("userId",userId_);
            postParams.add("resourcesId",resourcesId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParams,headers);
            String respText = restTemplate.postForObject(fetch_permission_url, httpEntity, String.class);
            log.info("请求13返回的数据：{}", respText);
            jsonObject = JSONObject.parseObject(respText);
        }
        return jsonObject;
    }
}
