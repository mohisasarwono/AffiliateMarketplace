/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.User;
import com.ThesisProject.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */

@RestController
public class TestingController {
    
     @Autowired
     UserRepositories userRepo;
    
    @RequestMapping(value = "/")
    public String saidHello(){
        return "Hello there, it's running";
    }
    
    @RequestMapping(value = "addAdmin",method = RequestMethod.POST)
    public String addAdmin(){
        if(emailChecker("admin@admin.com")==1){
        userRepo.save(new User((long)1,1,"Admin","admin123","admin@admin.com"));
        return "Welcome to this project admin";
        }
        return "You have already sign up";
    }
    
    public int emailChecker(String email){
        if(userRepo.getByEmail(email)==null)
            return 1;
        return 0;
    }
    
}
