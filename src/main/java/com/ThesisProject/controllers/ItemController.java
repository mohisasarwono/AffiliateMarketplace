/*)
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Item;
import com.ThesisProject.repositories.ItemRepositories;
import com.ThesisProject.repositories.StoreRepositories;
import com.ThesisProject.wrappers.ItemWrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(path = "apis/item")
public class ItemController {
    
    @Autowired
    ItemRepositories itemRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    String message;
    
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public String addItem(@RequestBody ItemWrapper itemWrapper) throws ParseException{
        try {
            Item item = new Item(itemWrapper.getName(), itemWrapper.getPrice(), itemWrapper.getCommissionPriceOrPercentage(), (byte)1, itemWrapper.getCommissionStatus(), new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").parse(itemWrapper.getExpiredDate()), storeRepo.getOne(itemWrapper.getStoreId()));
            if(itemWrapper.getDescription()!=null)
                item.setDescription(itemWrapper.getDescription());
            if(itemWrapper.getQty()!=null)
                item.setQty(itemWrapper.getQty());
            if(itemWrapper.getRecurring()!=null)
                item.setRecurring(itemWrapper.getRecurring());
            if(itemWrapper.getType()!=null)
                item.setType(itemWrapper.getType());
            if(itemWrapper.getPhotoURL()!=null)
                item.setPhotoUrl(itemWrapper.getPhotoURL());
            itemRepo.save(item);
            message="Success adding newItem, with itemId: "+item.getId()+" and itemName: "+item.getName();
            return message;
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            message="Error Occured, Please Input Data Correctly";
            return message;
        }
    
    }
    
    @RequestMapping(value="update", method = RequestMethod.POST)
    public String updateItem(@RequestBody ItemWrapper itemWrapper){
    try{
        Item item = itemRepo.getOne(itemWrapper.getId());
        if(itemWrapper.getName()!=null)
            item.setName(itemWrapper.getName());
        if(itemWrapper.getCommissionPriceOrPercentage()!=null)
            item.setCommissionPriceOrPercentage(itemWrapper.getCommissionPriceOrPercentage());
        if(itemWrapper.getDescription()!=null)
            item.setDescription(itemWrapper.getDescription());
        if(itemWrapper.getExpiredDate()!=null)
            item.setExpiredDate(new Date(Date.parse(itemWrapper.getExpiredDate())));
        if(itemWrapper.getPrice()!=null)
            item.setPrice(itemWrapper.getPrice());
        if(itemWrapper.getRecurring()!=null)
            item.setRecurring(itemWrapper.getRecurring());
        if(itemWrapper.getType()!=null)
            item.setType(itemWrapper.getType());
        if(itemWrapper.getPhotoURL()!=null)
            item.setPhotoUrl(itemWrapper.getPhotoURL());
        itemRepo.save(item);
        message="Success updating newItem, with itemId: "+item.getId()+" and itemName: "+item.getName();
        return message;
    }catch(Exception e){
        e.printStackTrace();
        message="Error Occured, Please Input Data Correctly";
        return message;
    }
    }
    
    @RequestMapping(value = "delete", method=RequestMethod.GET,produces="application/json")
    public void deleteItem(@RequestParam("itemId") Long id){
        Item thisItem = itemRepo.getOne(id);
        thisItem.setStatus((byte)0);
        itemRepo.save(thisItem);
    }
    
    @RequestMapping(value = "getByStore", method=RequestMethod.GET,produces="application/json")
    public List<Item> getByStore(@RequestParam("storeId") Long storeId){
       return itemRepo.getByStoreAndStatus(storeId);
    }
    
    @RequestMapping(value = "getAllItem", method=RequestMethod.GET,produces="application/json")
    public List<Item> getAllItem(){
       return itemRepo.getAllLimit();
    }
    
    @RequestMapping(value="getById", method=RequestMethod.GET,produces="application/json")
    public Item getById(@RequestParam(name = "id")Long id){
        return itemRepo.getItemById(id);
    }
}
