/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.services;

import com.ThesisProject.models.Promoter;
import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.repositories.StoreRepositories;
import com.ThesisProject.repositories.UserRepositories;
import com.ThesisProject.wrappers.UserWrapper;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LENOVO
 */
@Service
public class UserServices {
    @Autowired
    UserRepositories userRepo;
    
    @Autowired
    PromoterRepositories promoRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    
    public int emailChecker(String email){
        if(userRepo.getByEmail(email)==null)
            return 1;
        return 0;
    }
    
    public boolean isEmailVaild(String email){
        Matcher emailMatcher = Pattern.compile( "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email);
        return emailMatcher.matches();
    }
    
    public String saveData(UserWrapper userWrapper, Byte userType){
    if(userType==1){
        promoRepo.save(new Promoter(userWrapper.getName(), userWrapper.getAddress(), userWrapper.getPhoneNumber(), userWrapper.getPhotoProfileUrl(), userWrapper.getEmail(), userWrapper.getPassword(), (byte)1));
        return "Promoter";
    }
    storeRepo.save(new Store(userWrapper.getName(), userWrapper.getAddress(), userWrapper.getPhoneNumber(), userWrapper.getPhotoProfileUrl(), userWrapper.getEmail(), userWrapper.getPassword(), (byte)1,userWrapper.getStoreDescription()));
    return "Store";
    }
    
    public String generateReferralCode(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        System.out.println(generatedString);
        return generatedString;
    }
    
}
