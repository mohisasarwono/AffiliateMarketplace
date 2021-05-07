/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Store;
import com.ThesisProject.repositories.StoreRepositories;
import com.ThesisProject.wrappers.ItemWrapper;
import com.ThesisProject.wrappers.StoreWrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "apis/store")
public class StoreController {
    
    @Autowired
    private StoreRepositories storeRepo;
    
    @Autowired
    private ItemController itemController;
    
    String message;
    
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public @ResponseBody String add(StoreWrapper storeWrapper){
        return initData(storeWrapper, new Store());
    }
    
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public @ResponseBody String update(StoreWrapper storeWrapper){
        return initData(storeWrapper, storeRepo.getOne(storeWrapper.getId()));
    }
    
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public @ResponseBody String delete(@RequestParam(name = "id", required = true) Long id){
        try{
        Store store = storeRepo.getOne(id);
        store.setStatus((byte)0);
        storeRepo.save(store);
        message="Successfully delete store";
        }
        catch(Exception e){
            e.printStackTrace();
            message="Error Occured";
        }
        return message;
    }
    
    
    public String initData(StoreWrapper storeWrapper, Store store){
        try{
        if(storeWrapper.getAddress()!=null)
            store.setAddress(storeWrapper.getAddress());
        if(storeWrapper.getEmail()!=null)
            store.setEmail(storeWrapper.getEmail());
        if(storeWrapper.getName()!=null)
            store.setName(storeWrapper.getName());
        if(storeWrapper.getPhoneNumber()!=null)
            store.setPhoneNumber(storeWrapper.getPhoneNumber());
        if(storeWrapper.getPhotoProfileUrl()!=null)
            store.setPhotoProfileUrl(storeWrapper.getPhotoProfileUrl());
        if(storeWrapper.getStoreDescription()!=null)
            store.setStoreDescription(storeWrapper.getStoreDescription());
        if(storeWrapper.getType()!=null)
            store.setType(storeWrapper.getType());
        store.setStatus((byte)1);
        storeRepo.save(store);
        if(storeWrapper.getItems()!=null){
         for(ItemWrapper item: storeWrapper.getItems()){
                item.setStoreId(store.getId());
                itemController.addItem(item);
                }
           }
        message ="Success Adding/Updating Store";
        }catch(Exception e){
            e.printStackTrace();
            message = "Error Occured";
        }
       return message;
    }
    
}
