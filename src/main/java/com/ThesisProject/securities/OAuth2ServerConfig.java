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
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
//import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// *
// * @author ivanferianda
// */
//@Configuration
////@EnableWebSecurity
//public class OAuth2ServerConfig {
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//    
//    @Autowired
//    private UserAccessService userAccessSecurity;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(userAccessSecurity)
//            .passwordEncoder(passwordEncoder());
//    }
//    
//    
//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory());
//        //return new InMemoryTokenStore(); 
//        //change management token to redis factory
//    }
//    
//    @Value("${redis.address}")
//    private String redisAddress;
//    @Value("${redis.port}")
//    private Integer redisPort;
//    @Value("${redis.password}")
//    private String password;
//
//    @Bean 
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory cf = new JedisConnectionFactory();
//        cf.setHostName( redisAddress );
//        cf.setPort( redisPort );
//        cf.setPassword(password);
//        cf.afterPropertiesSet();
//        return cf;
//    }
//    
//    @Bean
//    @Autowired
//    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
//        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//        handler.setTokenStore(tokenStore);
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//        handler.setClientDetailsService(clientDetailsService);
//        return handler;
//    }
//    
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.cors().and();
////            //other config
////    }
//// 
////    @Bean
////    CorsConfigurationSource corsConfigurationSource()
////    {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("*"));
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST",  "PUT"));
////        configuration.setAllowedHeaders(Arrays.asList("*"));
////        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
////        configuration.setAllowCredentials(true);
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
////    @Override
////    public void configure(WebSecurity web) throws Exception {
////        web.ignoring().antMatchers(HttpMethod.GET, "/**");
////    }
//}
