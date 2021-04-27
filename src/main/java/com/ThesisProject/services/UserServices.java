/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.services;

import com.ThesisProject.models.Promoter;
import com.ThesisProject.models.ReferralCode;
import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.repositories.ReferralCodeRepositories;
import com.ThesisProject.repositories.StoreRepositories;
//import com.ThesisProject.repositories.UserRepositories;
import com.ThesisProject.wrappers.UserWrapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
//    @Autowired
//    UserRepositories userRepo;
//    
    @Autowired
    PromoterRepositories promoRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    @Autowired
    ReferralCodeRepositories referralCodeRepo;
    
    public int emailChecker(String email){
        if(promoRepo.getByEmail(email)==null)
            return 1;
        return 0;
    }
    
    public String encryptPassword(String password){
        String encryptedPass = "";
        try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        encryptedPass= hash.toString();
            System.out.println(encryptedPass);
        }catch(Exception e){
        e.printStackTrace();
        }
        return encryptedPass;
    }
    
    public boolean isEmailVaild(String email){
        Matcher emailMatcher = Pattern.compile( "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email);
        return emailMatcher.matches();
    }
    
    public String saveData(UserWrapper userWrapper, Byte userType){
    if(userType==1){
        Promoter promoter =new Promoter(userWrapper.getName(), userWrapper.getAddress(), userWrapper.getPhoneNumber(), userWrapper.getPhotoProfileUrl(), userWrapper.getEmail(), encryptPassword(userWrapper.getPassword()), (byte)1); 
        promoRepo.save(promoter);
        referralCodeRepo.save(new ReferralCode(generateReferralCode(), (byte)1,promoter));
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
