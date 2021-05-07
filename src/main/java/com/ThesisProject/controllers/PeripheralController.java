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
import java.time.Instant;
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
            
    String message;
    
    @RequestMapping(value = "promote", method=RequestMethod.GET)
    public String promoteItem(@RequestParam(name = "promoterId", required = true)Long promoterId,@RequestParam(name = "status",required = true,defaultValue = "1")Byte status,@RequestParam(name ="itemId",required = true)Long itemId,@RequestParam(name="peripheralLink",required = true)String peripheralLink){
        try{
        Peripheral thisPeripheral = peripheralRepo.getByPeripheralLink(peripheralLink);
        if(thisPeripheral==null){
            Item item =  itemRepo.getOne(itemId);
            ReferralCode referralCode = referralCodeRepo.findByPromoter(promoterId);
            thisPeripheral = new Peripheral(peripheralLink, 0, referralCode , item);
            peripheralRepo.save(thisPeripheral);
            message ="Success Promote this Item";
        }else{
            thisPeripheral.setStatus((byte)1);
            peripheralRepo.save(thisPeripheral);
            message ="Success Promote this Item";
        }    
        }catch(Exception e){
            e.printStackTrace();
            message = "Error";
        }
        return message;
    }
    
    @RequestMapping(value = "counter", method=RequestMethod.GET)
    public String clickCounter(@RequestParam(name="peripheralLink")String peripheralLink){
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
    public List<Peripheral> getAllPeripheralByPromoter(@RequestParam(name="promoterId",required = true)Long promoterId){
        return peripheralRepo.getAllByReferralCode(referralCodeRepo.findByPromoter(promoterId));
    }
    
    @RequestMapping(value = "cancelPromote", method = RequestMethod.GET)
    public String cancelPromote(@RequestParam(name="peripheralId",required = true)Long peripheralId){
        try{
        Peripheral thisPeripheral = peripheralRepo.getOne(peripheralId);
        thisPeripheral.setStatus((byte)0);
        peripheralRepo.save(thisPeripheral);
        message ="Cancel promote";
        }catch(Exception e){
            e.printStackTrace();
            message = "Error Occured";
        }
        return message;
    }
}
