package com.github.izerui.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serv on 2014/11/16.
 */
@Configuration
@ConditionalOnWebApplication
public class DruidWebAutoConfiguration {

    @Bean(name = "druidWebStatFilter")
    public FilterRegistrationBean druidWebStatFilter(DruidProperties properties){
        FilterRegistrationBean filterRegistrationBean =  new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParameters = new HashMap<String, String>(1);
        initParameters.put("exclusions" , properties.getExclusions());
        initParameters.put("sessionStatMaxCount" , properties.getSessionStatMaxCount().toString());
        initParameters.put("sessionStatEnable",String.valueOf(properties.isSessionStatEnable()));
        if(properties.getPrincipalSessionName()!=null){
            initParameters.put("principalSessionName",properties.getPrincipalSessionName());
        }
        initParameters.put("profileEnable",String.valueOf(properties.isProfileEnable()));
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }


    @Bean
    public ServletRegistrationBean druid(DruidProperties properties) throws SQLException {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> map = new HashMap<String, String>();
        map.put("resetEnable",String.valueOf(properties.isResetEnable()));
        if(properties.getLoginUsername()!=null){
            map.put("loginUsername",properties.getLoginUsername());
        }
        if(properties.getLoginPassword()!=null){
            map.put("loginPassword",properties.getLoginPassword());
        }
        if(properties.getJmxUrl()!=null){
            map.put("jmxUrl",properties.getJmxUrl());
        }
        if(properties.getJmxUsername()!=null){
            map.put("jmxUsername",properties.getJmxUsername());
        }
        if(properties.getJmxPassword()!=null){
            map.put("jmxPassword",properties.getJmxPassword());
        }
        registrationBean.setInitParameters(map);
        return registrationBean;
    }


}
