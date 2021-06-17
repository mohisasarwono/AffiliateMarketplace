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
import com.ThesisProject.wrappers.MessageWrapper;
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
    MessageWrapper messageWrapper;
    
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public MessageWrapper addItem(@RequestBody ItemWrapper itemWrapper) throws ParseException{
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
            messageWrapper = new MessageWrapper("Success adding newItem, with itemId: "+item.getId()+" and itemName: "+item.getName(),"AdIt-T",true);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            messageWrapper=new MessageWrapper("Error Occured, Please Input Data Correctly","AdIt-F",false);
        }
        return messageWrapper;
    
    }
    
    @RequestMapping(value="update", method = RequestMethod.POST)
    public MessageWrapper updateItem(@RequestBody ItemWrapper itemWrapper){
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
        messageWrapper = new MessageWrapper("Update adding newItem, with itemId: "+item.getId()+" and itemName: "+item.getName(),"UpIt-T",true);
    }catch(Exception e){
        e.printStackTrace();
         messageWrapper=new MessageWrapper("Error Occured, Please Input Data Correctly","UpIt-F",false);
    }
        return messageWrapper;
    }
    
    @RequestMapping(value = "delete", method=RequestMethod.GET,produces="application/json")
    public MessageWrapper deleteItem(@RequestParam("itemId") Long id){
        Item thisItem = itemRepo.getOne(id);
        if(thisItem!=null){ 
            thisItem.setStatus((byte)0);
            itemRepo.save(thisItem);
            messageWrapper = new MessageWrapper("Successed Deleting Item with Id : "+id.toString(),"DlIt-T",true);
        }else
            messageWrapper = new MessageWrapper("Failed Deleting Item","DlIt-F",false);
        return messageWrapper;
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
    
    public boolean calculateQty(Long itemId, Double thisQty){
        Item thisItem = itemRepo.getItemById(itemId);
        if(thisItem.getQty()>thisQty&&thisItem.getQty()>0){
            thisItem.setQty(thisItem.getQty()-thisQty);
            itemRepo.save(thisItem);
            return true;}
        return false;
    }
}
