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
    
    @RequestMapping(value = "updateReferral", method = RequestMethod.GET)
    public String updateReferral(@RequestParam(name = "referralCode", required = false)String referralCode,@RequestParam(name = "promoterId", required = true)Long promoterId,@RequestParam(name = "status",required = true,defaultValue = "1")Byte status){
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
        if(status ==2){
            if(referralCodeRepo.checkReferralCode(referralCode)==null)
            saveData(thisReferral, referralCode, BY_USER);
            else 
                message ="Referral Code has been used by other";
        }else{
            if(referralCodeRepo.checkReferralCode(referralCode)==null)
            saveData(thisReferral, referralCode, BY_SYSTEM);
            else 
                message ="Referral Code has been used by other";
        }
        referralCodeRepo.save(thisReferral);
        message ="Success Updating Referral Code";
        return message;
    }
    
    @RequestMapping(value = "getGeneratedBySystem", method = RequestMethod.GET)
    public String getGenerateBySystem(){
        return userServices.generateReferralCode();
    }
   
    @RequestMapping(value = "hide", method = RequestMethod.GET)
    public void delete(@RequestParam(name = "id")Long id){
        ReferralCode thisReferral = referralCodeRepo.getOne(id);
        thisReferral.setStatus((byte)0);
        referralCodeRepo.save(thisReferral);
    }
    
    @RequestMapping(value="getByPromoter", method = RequestMethod.GET)
    public ReferralCode getByPromoter(@RequestParam(name = "promoterId", required = true)Long promoterId){
        return referralCodeRepo.findByPromoter(promoterId);
    }
    
    public void saveData(ReferralCode thisReferral, String referralCode, Byte status){
        thisReferral.setReferralByUser(referralCode);
        thisReferral.setStatus(status);
    }
}

