/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.repositories.ReferralCodeRepositories;
import com.ThesisProject.services.UserServices;
import com.ThesisProject.wrappers.MessageWrapper;
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
    private Byte BY_SYSTEM =1;
    private Byte BY_USER =2;
    
    MessageWrapper messageWrapper = new MessageWrapper();
    
    @RequestMapping(value = "getGeneratedByUser", method = RequestMethod.GET)
    public MessageWrapper generatedByUser(@RequestParam(name = "referralCode", required = false)String referralCode,@RequestParam(name = "promoterId", required = true)Long promoterId){
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
           if(thisReferral.getReferralByUser()==referralCode){
               messageWrapper = new MessageWrapper("Your inputted referral code is the same as your old referral code one","GGBU-F",false);
           }else if(referralCodeRepo.checkReferralCode(referralCode).isEmpty()){
               thisReferral.setReferralByUser(referralCode);
               thisReferral.setStatus(BY_USER);
               referralCodeRepo.save(thisReferral);
              messageWrapper = new MessageWrapper("Successed Updating Referral Code","GGBU-T",true);
           }else{
                 messageWrapper = new MessageWrapper("Referral Code Has Been Being Used by Other User","GGBU-F",false);}
        return messageWrapper;
    }
    
    @RequestMapping(value = "getGeneratedBySystem", method = RequestMethod.GET)
    public MessageWrapper getGenerateBySystem(@RequestParam(name = "promoterId", required = true)Long promoterId){
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
        if(thisReferral!=null){
        String generatedBySystem =userServices.generateReferralCode();
        thisReferral.setReferralBySystem(generatedBySystem);
        thisReferral.setStatus(BY_SYSTEM);
        referralCodeRepo.save(thisReferral);
        messageWrapper.setMessage(generatedBySystem);
        messageWrapper.setStatus(true);
        messageWrapper.setCode("GRC-T");
        return messageWrapper;}
        messageWrapper.setMessage("Error Occured, Please Try Again");
        messageWrapper.setStatus(false);
        messageWrapper.setCode("GRC-F");
        return messageWrapper;
    }
   
    @RequestMapping(value = "hide", method = RequestMethod.GET)
    public void delete(@RequestParam(name = "id")Long id){
        ReferralCode thisReferral = referralCodeRepo.getOne(id);
        thisReferral.setStatus((byte)0);
        referralCodeRepo.save(thisReferral);
    }
    
    @RequestMapping(value = "changeStatus", method=RequestMethod.GET)
    public MessageWrapper changeStatus(@RequestParam(name="promoterId")Long promoterId, @RequestParam(name="status")Byte status){
        try{
        ReferralCode thisReferral = referralCodeRepo.findByPromoter(promoterId);
        thisReferral.setStatus(status);
        referralCodeRepo.save(thisReferral);
         messageWrapper = new MessageWrapper("Success Changing Your Referral Code","UPDRC-T", true);
        }catch(Exception e){
            e.printStackTrace();
            messageWrapper = new MessageWrapper("Error : Something Went Wrong","UPDRC-F",false);
        }
        return messageWrapper;
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

