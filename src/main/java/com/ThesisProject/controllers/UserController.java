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
//import com.ThesisProject.repositories.UserRepositories;
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
//    @Autowired
//    UserRepositories userRepo;
    
    @Autowired
    PromoterRepositories promoRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    @Autowired
    UserServices userServices;
    
    private String message;
    
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String register(@RequestBody UserWrapper userWrapper){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
                userServices.saveData(userWrapper, (byte)1);
            System.out.println("Berhasil");
            message="Hello, "+userWrapper.getName();
        }else{
            System.out.println("Gagal");
            message="Error";
        }
        return message;
    }
    
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public @ResponseBody Promoter login(@RequestParam(name = "email",required = true)String email,@RequestParam(name = "password",required = true)String passrword){
            Promoter promoter = promoRepo.findByEmailAndPassword(email, userServices.encryptPassword(passrword));
            return promoter;
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
