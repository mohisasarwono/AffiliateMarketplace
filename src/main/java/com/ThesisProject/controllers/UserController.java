/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.configuration.HibernateProxyTypeAdapter;
import com.ThesisProject.models.Promoter;
import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.repositories.ReferralCodeRepositories;
import com.ThesisProject.repositories.StoreRepositories;
//import com.ThesisProject.repositories.UserRepositories;
import com.ThesisProject.services.UserServices;
import com.ThesisProject.wrappers.MessageWrapper;
import com.ThesisProject.wrappers.UserWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    MessageWrapper messageWrapper;
    
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public MessageWrapper register(@RequestBody UserWrapper userWrapper){
        if(userServices.emailChecker(userWrapper.getEmail())==1&&userServices.isEmailVaild(userWrapper.getEmail())){
                saveData(userWrapper,new Promoter(),true);
            messageWrapper = new MessageWrapper("Hello, "+userWrapper.getName()+" Welcome","LG-T", true);
        }else{
            messageWrapper = new MessageWrapper("Please insert data correctly","LG-F", false);
        }
        return messageWrapper;
    }
    
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public @ResponseBody Promoter login(@RequestParam(name = "email",required = true)String email,@RequestParam(name = "password",required = true)String passrword){
            Promoter promoter = promoRepo.findByEmailAndPassword(email, passrword);
            if(promoter==null)
                return new Promoter();
            return promoter;
    }
    
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public MessageWrapper update(@RequestBody UserWrapper userWrapper){
        Promoter promoter = promoRepo.getOne(userWrapper.getId());
        try{
            String DoB = saveData(userWrapper,promoter,false);
            System.out.println(DoB);
            GsonBuilder gsonB = new GsonBuilder().registerTypeAdapter(Promoter.class, new JsonSerializer<Promoter>(){
                @Override
                public JsonElement serialize(Promoter promoter, Type type, JsonSerializationContext jsonSerializationContext) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", promoter.getId());
                    jsonObject.addProperty("name", promoter.getName());
                    jsonObject.addProperty("address", promoter.getAddress());
                    jsonObject.addProperty("phoneNumber", promoter.getPhoneNumber());
                    jsonObject.addProperty("photoProfileUrl", promoter.getPhotoProfileUrl());
                    jsonObject.addProperty("email", promoter.getEmail());
                    jsonObject.addProperty("gender", promoter.getGender());
                    jsonObject.addProperty("DoB", DoB);
                    jsonObject.addProperty("status", promoter.getStatus());
                    jsonObject.addProperty("commissionMoney", promoter.getCommissionMoney().doubleValue());
                    return jsonObject;
                }
            });
            gsonB.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
            Gson gson = gsonB.create();
            messageWrapper = new MessageWrapper("Profile has been updated", gson.toJson(promoter), true);
        }catch(Exception e){
            e.printStackTrace();
            messageWrapper = new MessageWrapper("Please insert data correctly", "UPD-F", false);
        }
        return messageWrapper;
    }
    
    public String saveData(UserWrapper userWrapper, Promoter promoter,boolean isRegis){
        String dateOfBirth = promoter.getDoB().toString();
        if(userWrapper.getName()!=null)
            promoter.setName(userWrapper.getName());
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
        try{
        if(userWrapper.getDoB()!=null){
            promoter.setDoB(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userWrapper.getDoB()));
            dateOfBirth = userWrapper.getDoB();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        promoter.setStatus((byte)1);
        if(promoter.getCommissionMoney()==null)
            promoter.setCommissionMoney((double)0);
        if(userWrapper.getGender()!=null)
            promoter.setGender(userWrapper.getGender());
        promoRepo.save(promoter);
        if(isRegis==true)
        referralCodeRepo.save(new ReferralCode(userServices.generateReferralCode(), (byte)1,promoter));
        return dateOfBirth;
    }
    
}
