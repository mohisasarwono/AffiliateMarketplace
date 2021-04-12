/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Promoter;
import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.repositories.StoreRepositories;
import com.ThesisProject.repositories.UserRepositories;
import com.ThesisProject.services.UserServices;
import com.ThesisProject.wrappers.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping(path = "apis/user")
public class UserController {
    @Autowired
    UserRepositories userRepo;
    
    @Autowired
    PromoterRepositories promoRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    @Autowired
    UserServices userServices;
    
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public void register(@RequestBody UserWrapper userWrapper, @RequestParam(name = "type",required = true,defaultValue = "1")Byte userType){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
            userServices.saveData(userWrapper, userType);
            System.out.println("Berhasil");
        }else{
            System.out.println("Gagal");
        }
    }
    
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public @ResponseBody Long login(@RequestParam(name = "email",required = true)String email,@RequestParam(name = "password",required = true)String passrword,@RequestParam(name="type",defaultValue = "1")Byte userType){
        if(userType==1){
            Promoter promoter = promoRepo.findByEmailAndPassword(email, passrword);
            if(promoter!=null){
                return promoter.getId();
            }
        }else{
            Store store = storeRepo.findByEmailAndPassword(email, passrword);
            if(store!=null){
                return store.getId();
            }
        }
        return (long)0;
    }
    
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void update(@RequestBody UserWrapper userWrapper, @RequestParam(name = "type",required = true,defaultValue = "1")Byte userType){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
            userServices.saveData(userWrapper, userType);
            System.out.println("Berhasil");
        }else{
            System.out.println("Gagal");
        }
    }
    
}
