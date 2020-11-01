///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ThesisProject.securities;
//
//import com.ameerarestapi.exception.UserUnAuthorizedUserRoleException;
//import com.ameerarestapi.exception.UserNotActivatedException;
//import com.ameerarestapi.model.master.Authority;
//import com.ameerarestapi.model.master.User;
//import com.ameerarestapi.model.master.UserRoleAccess;
//import com.ameerarestapi.repositories.master.UserRepositories;
//import com.ameerarestapi.repositories.master.UserRoleAccessRepositories;
//import java.util.ArrayList;
//import java.util.Collection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author ivanferianda
// */
//@Component("userAccessService")
//public class UserAccessService implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    private final Logger log = LoggerFactory.getLogger(UserAccessService.class);
//    
//    private static final long ROLE_OWNER = 1;
//    private static final byte STATUS_ACTIVE = 1;
//    private static final byte STATUS_INACTIVE = 0; 
//
//    @Autowired
//    private UserRepositories userRepositories;
//    
//    @Autowired
//    private UserRoleAccessRepositories userRoleAccessRepositories;
//    
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(final String login) { 
//
//        User userFromDatabase;
//        if(login.contains("@")) {
//            userFromDatabase = userRepositories.findByEmailIgnoreCaseAndStatus(login,STATUS_ACTIVE);
//        } else {
//            userFromDatabase = userRepositories.findByUsername(login);
//            
//            if(userFromDatabase == null){
//                try{
//                    Long userId = Long.parseLong(login);
//                    userFromDatabase = userRepositories.findOne(userId);
//                }catch(Exception e){
//                    // failed parse
//                }
//            }
//        }
//        
//        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        org.springframework.security.core.userdetails.User currRequestClientId = (org.springframework.security.core.userdetails.User)object;
//        
//                
//        if (userFromDatabase == null) {
//            throw new UsernameNotFoundException("User " + login + " was not found in the database");
//        } else if (userFromDatabase.getStatus() != 1) {
//            throw new UserNotActivatedException("User " + login + " is not activated");
//        } else if (!userFromDatabase.isEnableLoginApp()) {
//            throw new UserNotActivatedException("User " + login + " is not enabled login");
//        }
////        else if(userFromDatabase.getUserRole().getId() == 2){
////            throw new UserUnAuthorizedUserRoleException("User Role is UnAuthorized ");
////        }
//        
//        if(userFromDatabase != null){
//            if(userFromDatabase.getUserRole().getId() != ROLE_OWNER)
//            {
//                UserRoleAccess roleAccess = userRoleAccessRepositories.findByPrincipleAndUserRole(userFromDatabase.getPrinciple(), userFromDatabase.getUserRole());
//                if(roleAccess != null){
//                    if(!roleAccess.isIsEnableAccessBackoffice() && "ameera-persistent".equals(currRequestClientId.getUsername())){
//                        throw new UserUnAuthorizedUserRoleException("User Role is UnAuthorized");
//                    }else if(!roleAccess.isIsEnableAccessMobileAppPOS() && "ameera-client".equals(currRequestClientId.getUsername())){
//                        throw new UserUnAuthorizedUserRoleException("User Role is UnAuthorized");
//                    }
//                }else{
//                    throw new UserUnAuthorizedUserRoleException("User Role is UnAuthorized");
//                }
//            }
//            
//            if(!userFromDatabase.isEnableLoginApp()){
//                throw new UserNotActivatedException("User " + login + " is not have permission to login");
//            }
//        }
//        
//
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Authority authority : userFromDatabase.getAuthorities()) {
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
//            grantedAuthorities.add(grantedAuthority);
//        }
//
//        return new org.springframework.security.core.userdetails.User(userFromDatabase.getId().toString(), userFromDatabase.getPassword(), grantedAuthorities);
//
//    }
//    
//}
