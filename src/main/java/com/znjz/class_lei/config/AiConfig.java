package com.znjz.class_lei.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Set;

public class AiConfig {
    public static String Base_url="http://114.132.240.69:5000";
    public static final String REGISTER = "/register"; //注册
    public static final String embedding = "/embedding";//提交
    public static final String takeIn = "/take_in";//识别
    public static final String haveRegister = "/haveRegister";//是否已经注册

    //提交
    public JSONObject commit(){
        return post(embedding, new HashMap<String,Object>());
    }


    public JSONObject post(String path,HashMap<String,Object> params){
        //请求路径
        String url = AiConfig.Base_url+"/embedding ";
        //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        // json对象
        JSONObject jsonObject = new JSONObject();
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        Set<String> keySet = params.keySet();
        for (String s : keySet) {
            body.add(s,params.getOrDefault(s,null));
        }
        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            //解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println(jsonObject.toJSONString());
            return jsTemp;
        }catch (Exception e){
            System.out.println(e);
        }
        return  null;
    }

    //请求注册
    public JSONObject request(MultipartFile file, String uid){
        //请求路径
        String url = AiConfig.Base_url;
        //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        // json对象
        JSONObject jsonObject = new JSONObject();
        LinkedMultiValueMap body=new LinkedMultiValueMap();

        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            //解析返回的数据
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            System.out.println(jsonObject.toJSONString());
            return jsTemp;
        }catch (Exception e){
            System.out.println(e);
        }
        return  null;
    }




}
