///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ThesisProject.securities;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//
///**
// *
// * @author ivanferianda
// */
//@Configuration
//@EnableResourceServer
////@EnableWebSecurity
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
//    
////    @Value("${doku.whitelist.ip}")
//    private String ip;
////    = env.getProperty("doku.whitelist.ip", String[].class);;
//     
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(AuthorizationServerConfig.RESOURCE_ID);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        
//          http.authorizeRequests()
//                .antMatchers("/greeting").permitAll()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/register/**").permitAll()
//                .antMatchers("/midtrans/**").authenticated()
//                .antMatchers("/application/receive/espay/*").permitAll()
//                .antMatchers("/application/receive/doku/notify")
////                  .access(""+ip+"")
////                  .access("hasIpAddress('103.10.129.9') or hasIpAddress('103.10.130.35') or hasIpAddress('119.2.42.134')")
////                  .access("hasIpAddress('125.166.205.172') or hasIpAddress('127.0.0.1') or hasIpAddress('192.168.101.67') or hasIpAddress('192.168.101.10') or hasIpAddress('0:0:0:0:0:0:0:1')")
//                                                             .access("hasIpAddress('103.10.129.0') "
//                                                                + "or hasIpAddress('103.10.129.1') "
//                                                                + "or hasIpAddress('103.10.129.2') "
//                                                                + "or hasIpAddress('103.10.129.3') "
//                                                                + "or hasIpAddress('103.10.129.4') "
//                                                                + "or hasIpAddress('103.10.129.5') "
//                                                                + "or hasIpAddress('103.10.129.6') "
//                                                                + "or hasIpAddress('103.10.129.7') "
//                                                                + "or hasIpAddress('103.10.129.8') "
//                                                                + "or hasIpAddress('103.10.129.9') "
//                                                                + "or hasIpAddress('103.10.129.10') "
//                                                                + "or hasIpAddress('103.10.129.11') "
//                                                                + "or hasIpAddress('103.10.129.12') "
//                                                                + "or hasIpAddress('103.10.129.13') "
//                                                                + "or hasIpAddress('103.10.129.14') "
//                                                                + "or hasIpAddress('103.10.129.15') "
//                                                                + "or hasIpAddress('103.10.129.16') "
//                                                                + "or hasIpAddress('103.10.129.17') "
//                                                                + "or hasIpAddress('103.10.129.18') "
//                                                                + "or hasIpAddress('103.10.129.19') "
//                                                                + "or hasIpAddress('103.10.129.20') "
//                                                                + "or hasIpAddress('103.10.129.21') "
//                                                                + "or hasIpAddress('103.10.129.22') "
//                                                                + "or hasIpAddress('103.10.129.23') "
//                                                                + "or hasIpAddress('103.10.129.24') "
//                                                                + "or hasIpAddress('103.10.130.35') "
//                                                                + "or hasIpAddress('119.2.42.134') ")
////                  
//                .antMatchers("/master/**").authenticated()//.authenticated()//.authenticated()//.authenticated()//.authenticated()//permitAll()
//                .antMatchers("/transaction/**").authenticated()//.authenticated()//.authenticated()
//                .antMatchers("/report/**").authenticated()
//                .antMatchers("/configuration/**").authenticated()//.authenticated()//.authenticated()
//                .antMatchers("/master/dana/aztec/**").hasAnyAuthority("ROLE_TRUSTED_PARTNER_APP")
//                .antMatchers("/master/shopeeForm/aztec/**").hasAnyAuthority("ROLE_TRUSTED_PARTNER_APP")
//                .antMatchers("/franchise/**").authenticated()
////                .antMatchers("/setting/**").authenticated()
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
////         http.anonymous().disable()
////        .requestMatchers().antMatchers("/*/add/**","/*/update/**","/*/delete/**","/*/upload/**")
////        .and().authorizeRequests()
////        .antMatchers("/*/add/**","/*/update/**","/*/delete/**","/*/upload/**").authenticated()
////        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler()); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//}
