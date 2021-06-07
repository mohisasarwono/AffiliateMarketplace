/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.repositories.ReferralCodeRepositories;
import com.ThesisProject.services.UserServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping(path = "apis/referralCode")
public class ReferralCodeController {
    @Autowired
    ReferralCodeRepositories referralCodeRepo;
    
    @Autowired
    UserServices userServices;
    private String message;
    private Byte BY_SYSTEM =1;
    private Byte BY_USER =2;
    
    @RequestMapping(value = "getGeneratedByUser", method = RequestMethod.GET)
    public String generatedByUser(@RequestParam(name = "referralCode", required = false)String referralCode,@RequestParam(name = "promoterId", required = true)Long promoterId){
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
           if(thisReferral.getReferralByUser()==referralCode){
               message="Your inputted referral code is the same as your old referral code one";    
           }else if(referralCodeRepo.checkReferralCode(referralCode).isEmpty()){
               thisReferral.setReferralByUser(referralCode);
               thisReferral.setStatus(BY_USER);
               referralCodeRepo.save(thisReferral);
               message ="Success Updating Referral Code";
           }else{
               message ="Referral Code has been used by other";}
        return message;
    }
    
    @RequestMapping(value = "getGeneratedBySystem", method = RequestMethod.GET)
    public String getGenerateBySystem(@RequestParam(name = "promoterId", required = true)Long promoterId){
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
        if(thisReferral!=null){
        String generatedBySystem =userServices.generateReferralCode();
        thisReferral.setReferralBySystem(generatedBySystem);
        thisReferral.setStatus(BY_SYSTEM);
        referralCodeRepo.save(thisReferral);
        return generatedBySystem;}
        return "Error Occured, Please Try Again";
    }
   
    @RequestMapping(value = "hide", method = RequestMethod.GET)
    public void delete(@RequestParam(name = "id")Long id){
        ReferralCode thisReferral = referralCodeRepo.getOne(id);
        thisReferral.setStatus((byte)0);
        referralCodeRepo.save(thisReferral);
    }
    
    @RequestMapping(value = "changeStatus", method=RequestMethod.GET)
    public String changeStatus(@RequestParam(name="promoterId")Long promoterId, @RequestParam(name="status")Byte status){
        try{
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
        thisReferral.setStatus(status);
        referralCodeRepo.save(thisReferral);
        message="Success Changing Your Referral Code";
        }catch(Exception e){
            e.printStackTrace();
            message="Error : Something Went Wrong";
        }
        return message;
    }
    
    @RequestMapping(value="getByPromoter", method = RequestMethod.GET)
    public ReferralCode getByPromoter(@RequestParam(name = "promoterId", required = true)Long promoterId){
        return referralCodeRepo.findByPromoter(promoterId);
    }
    
    public void initData(ReferralCode thisReferral, String referralCode, Byte status){
        thisReferral.setReferralByUser(referralCode);
        thisReferral.setStatus(status);
    }
}

