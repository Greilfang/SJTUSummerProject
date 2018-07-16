package sjtusummerproject.signmicroservice.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sjtusummerproject.signmicroservice.DataModel.Domain.UserEntity;
import sjtusummerproject.signmicroservice.Service.InvokeUserService;

@Service
public class InvokeUserServiceImpl implements InvokeUserService {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    String baseUrl="http://user-microservice:8080";

    @Override
    public String AddUserMicroService(UserEntity user) {

        /* 发送给 UserMicroService */
        String url=baseUrl+"/User/Add";
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        //System.out.println("即将发请求");
        RestTemplate template = new RestTemplate();
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String,String> postbody = new LinkedMultiValueMap<>();
        postbody.add("username",user.getUsername());
        postbody.add("password",user.getPassword());
        postbody.add("email",user.getEmail());
        postbody.add("status",user.getStatus());

        // 1、使用postForObject请求接口
        String result = template.postForObject(url, postbody, String.class);

        //System.out.println("the result "+result);
        return result;
    }

    @Override
    public UserEntity QueryUserMicroService(String userName) {
        /* 发送给 UserMicroService */
        String url=baseUrl+"/User/Query?"+"username="+userName;
        /* 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了 */
        System.out.println("即将发请求2");
        RestTemplate template = new RestTemplate();
        UserEntity result = template.getForObject(url, UserEntity.class);

        System.out.println("the result in query user "+result);
        return result;
    }

    @Override
    public UserEntity validUser(String name, String password){
    	UserEntity userEntity = QueryUserMicroService(name);
    	if (userEntity == null || !userEntity.getPassword().equals(password) || userEntity.getStatus().equals("UnActive")){
    	    return null;
        }
        else {
    	    return userEntity;
        }

    }

    @Override
    public UserEntity GenerateUser(String username, String password, String Email, String status){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(Email);
        user.setStatus("UnActive");
        return user;
    }
}

