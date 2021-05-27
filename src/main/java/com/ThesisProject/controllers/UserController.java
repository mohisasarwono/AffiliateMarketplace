/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Promoter;
import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.repositories.ReferralCodeRepositories;
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
    ReferralCodeRepositories referralCodeRepo;
    
    @Autowired
    UserServices userServices;
    
    private String message;
    
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String register(@RequestBody UserWrapper userWrapper){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
                saveData(userWrapper);
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
            Promoter promoter = promoRepo.findByEmailAndPassword(email, passrword);
            return promoter;
    }
    
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void update(@RequestBody UserWrapper userWrapper){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
            saveData(userWrapper);
            System.out.println("Berhasil");
        }else{
            System.out.println("Gagal");
        }
    }
    
    public String saveData(UserWrapper userWrapper){
        Promoter promoter =new Promoter(); 
        if(userWrapper.getName()!=null)
            promoter.setName(message);
        if(userWrapper.getAddress()!=null)
            promoter.setAddress(userWrapper.getAddress());
        if(userWrapper.getPhoneNumber()!=null)
            promoter.setPhoneNumber(userWrapper.getPhoneNumber());
        if(userWrapper.getPassword()!=null)
            promoter.setPassword(userWrapper.getPassword());
        if(userWrapper.getEmail()!=null)
            promoter.setEmail( userWrapper.getEmail());
        if(userWrapper.getPhotoProfileUrl()!=null)
            promoter.setPhotoProfileUrl(userWrapper.getPhotoProfileUrl());
        if(userWrapper.getDoB()!=null)
            promoter.setDoB(userWrapper.getDoB());
        promoter.setStatus((byte)1);
        if(promoter.getCommissionMoney()==null)
            promoter.setCommissionMoney((double)0);
        if(userWrapper.getGender()!=null)
            promoter.setGender(userWrapper.getGender());
        promoRepo.save(promoter);
        referralCodeRepo.save(new ReferralCode(userServices.generateReferralCode(), (byte)1,promoter));
        return "Promoter";
    }
    
}
