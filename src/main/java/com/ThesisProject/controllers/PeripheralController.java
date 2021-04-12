/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.repositories.PeripheralRepositories;
import com.ThesisProject.repositories.ReferralCodeRepositories;
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
    
    @RequestMapping(value = "promote", method=RequestMethod.GET)
    public void promoteItem(@RequestParam(name = "referralCode", required = false)String referralCode,@RequestParam(name = "promoterId", required = true)Long promoterId,@RequestParam(name = "status",required = true,defaultValue = "1")Byte status){
        
    }
}
