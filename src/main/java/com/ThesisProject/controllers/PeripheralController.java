/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Item;
import com.ThesisProject.models.Peripheral;
import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.repositories.ItemRepositories;
import com.ThesisProject.repositories.PeripheralRepositories;
import com.ThesisProject.repositories.ReferralCodeRepositories;
import com.ThesisProject.wrappers.MessageWrapper;
import com.ThesisProject.wrappers.PeripheralWrapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping(path = "apis/peripheral")
public class PeripheralController {
    
    @Autowired
    ReferralCodeRepositories referralCodeRepo;
    
    @Autowired
    PeripheralRepositories peripheralRepo;
    
    @Autowired
    ItemRepositories itemRepo;
    
    MessageWrapper messageWrapper;
    
    @RequestMapping(value = "promote", method=RequestMethod.GET)
    public String promoteItem(@RequestParam(name = "promoterId", required = true)Long promoterId,@RequestParam(name ="itemId",required = true)Long itemId){
        String message = "Error Occured, Please Contact Our Customer Service";
        try{
        Peripheral thisPeripheral = peripheralRepo.getByItemAndReferral(itemId, referralCodeRepo.findByPromoter(promoterId).getId());
        if(thisPeripheral==null){
            String refCode="";
            Item item =  itemRepo.getOne(itemId);
            ReferralCode referralCode = referralCodeRepo.findByPromoter(promoterId);
            if(referralCode.getStatus()==1)
                refCode = referralCode.getReferralBySystem();
            else
                refCode = referralCode.getReferralByUser();
            String peripheralLink = "https://dummy-website-app.herokuapp.com/detail/"+item.getStore().getId()+"/"+item.getId()+"/"+refCode;
            thisPeripheral = new Peripheral(peripheralLink, 0, referralCode , item,(byte)1);
            thisPeripheral.setDuration(item.getExpiredDate());
            peripheralRepo.save(thisPeripheral);
            message = thisPeripheral.getPeripheralLink();
        }else{
            if(thisPeripheral.getStatus()==0){
                thisPeripheral.setStatus((byte)1);
                peripheralRepo.save(thisPeripheral);
            }
            message = thisPeripheral.getPeripheralLink();
        }    
        }catch(Exception e){
            e.printStackTrace();
        }
        return message;
    }
    
    @RequestMapping(value = "counter", method=RequestMethod.GET)
    public String clickCounter(@RequestParam(name="peripheralLink")String peripheralLink){
        String message;
        Peripheral thisPeripheral = peripheralRepo.getByPeripheralLink(peripheralLink);
        if(thisPeripheral!=null){
            thisPeripheral.setClickCounter(thisPeripheral.getClickCounter()+1);
            peripheralRepo.save(thisPeripheral);
            message="Update this counter with id : "+thisPeripheral.getId().toString();
        }
        else
            message="Data's not found";
        return message;
    }
    
    @RequestMapping(value = "getAllByPromoter", method = RequestMethod.GET)
    public List<PeripheralWrapper> getAllPeripheralByPromoter(@RequestParam(name="promoterId",required = true)Long promoterId){
        List<PeripheralWrapper> thisPeripheralWrappers = new ArrayList();
        List<Peripheral> peripheral = peripheralRepo.getAllByReferralCode(referralCodeRepo.findByPromoter(promoterId));
        for(Peripheral temp : peripheral){
            PeripheralWrapper pWTemp = new PeripheralWrapper();
            pWTemp.setId(temp.getId());
            pWTemp.setDuration(temp.getItem().getExpiredDate().toString());
            pWTemp.setPeripheralLink(temp.getPeripheralLink());
            pWTemp.setStatus(temp.getStatus());
            pWTemp.setClickCounter(temp.getClickCounter());
            pWTemp.setTotalTransaction(peripheralRepo.getTotalTransactionFromPeripheral(temp.getId()));
            thisPeripheralWrappers.add(pWTemp);
        }
        return thisPeripheralWrappers;
    }
    
    @RequestMapping(value = "getByPromoterAndItem", method = RequestMethod.GET)
    public Peripheral getByPromoterAndItem(@RequestParam(name="promoterId",required = true)Long promoterId,@RequestParam(name="itemId",required = true)Long itemId){
        return peripheralRepo.getByItemAndReferral(itemId, referralCodeRepo.findByPromoter(promoterId).getId());
    }
    
    @RequestMapping(value ="checkPeripheralLink", method = RequestMethod.GET)
    public Byte checkPeripheralLink(@RequestParam(name = "peripheralLink",required = true)String peripheralLink){
        try{
        return peripheralRepo.getByPeripheralLink(peripheralLink).getStatus();}
        catch(Exception e){
            e.printStackTrace();
            return (byte)0;
        }
    }
    
    @RequestMapping(value = "cancelPromote", method = RequestMethod.GET)
    public MessageWrapper cancelPromote(@RequestParam(name="peripheralId",required = true)Long peripheralId){
        try{
        Peripheral thisPeripheral = peripheralRepo.getOne(peripheralId);
        thisPeripheral.setStatus((byte)0);
        peripheralRepo.save(thisPeripheral);
        messageWrapper = new MessageWrapper("Successed to Cancel Promotion with peripheralLink :"+thisPeripheral.getPeripheralLink(),"CNL-PRM-T",true);
        }catch(Exception e){
            e.printStackTrace();
            messageWrapper = new MessageWrapper("Failed to  Cancel Promotion","CNL-PRM-F",false);
        }
        return messageWrapper;
    }
}
