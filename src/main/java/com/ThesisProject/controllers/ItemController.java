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

/**
 *
 * @author LENOVO
 */
@RequestMapping(path = "apis/item")
public class ItemController {
    
    @Autowired
    ItemRepositories itemRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    @RequestMapping(name = "add",method = RequestMethod.POST)
    public void addItem(@RequestBody ItemWrapper itemWrapper) throws ParseException{
        try {
            itemRepo.save(new Item(itemWrapper.getName(), itemWrapper.getDescription(), itemWrapper.getPrice(), itemWrapper.getCommissionPriceOrPercentage(), (byte)1, itemWrapper.getCommissionStatus(), new SimpleDateFormat("dd/MM/yyyy").parse(itemWrapper.getExpiredDate()), storeRepo.getOne(itemWrapper.getStoreId())));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    
    }
    
    @RequestMapping(name="update", method = RequestMethod.PUT)
    public void updateItem(@RequestBody ItemWrapper itemWrapper){
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
        itemRepo.save(item);
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    
    @RequestMapping(name = "delete", method = RequestMethod.GET)
    public void deleteItem(@RequestParam("itemId") Long id){
        Item thisItem = itemRepo.getOne(id);
        thisItem.setStatus((byte)0);
        itemRepo.save(thisItem);
    }
    
    @RequestMapping(name = "getByStore", method = RequestMethod.GET)
    public List<Item> getByStore(@RequestParam("storeId") Long storeId){
       return itemRepo.getByStoreAndStatus(storeId);
    }
    
    @RequestMapping(name = "getAllItem", method = RequestMethod.GET)
    public List<Item> getAllItem(){
       return itemRepo.getAll();
    }
    
    @RequestMapping(name="getById", method=RequestMethod.GET)
    public Item getById(@RequestParam(name = "id")Long id){
        return itemRepo.getOne(id);
    }
}
