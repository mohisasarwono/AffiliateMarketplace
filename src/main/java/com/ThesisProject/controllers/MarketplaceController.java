/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Item;
import com.ThesisProject.repositories.ItemRepositories;
import com.ThesisProject.repositories.StoreRepositories;
import com.ThesisProject.wrappers.MarketPlaceForItemWrapper;
import com.ThesisProject.wrappers.MarketPlaceForStoreWrapper;
import java.util.ArrayList;
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
@RequestMapping(path = "apis/marketplace")
public class MarketplaceController {
    @Autowired
    ItemRepositories itemRepo;
    
    @Autowired
    StoreRepositories storeRepo;
    
    @RequestMapping(value="getAllDataItem", method=RequestMethod.GET)
    public List<MarketPlaceForItemWrapper> getAllDataItem(@RequestParam(name = "limitData", required = true )Long limitData){
        List<MarketPlaceForItemWrapper> thisOutput = new ArrayList();
        List<Long> storeIds = storeRepo.getLimitDataForMarketplace(limitData);
        List<Object[]> thisData = itemRepo.getDataForMarketplace(storeIds);
        for(Object[] obj: thisData){
            thisOutput.add(new MarketPlaceForItemWrapper(Long.parseLong(obj[0].toString()), obj[1].toString(), obj[7].toString(), "imageUrl", Double.parseDouble(obj[2].toString()),Integer.parseInt(obj[6].toString()), obj[4].toString() , Long.parseLong(obj[5].toString()),Byte.parseByte(obj[3].toString())));
        }
        return thisOutput;
    }
    
    @RequestMapping(value="getAllDataStore", method=RequestMethod.GET)
    public List<MarketPlaceForStoreWrapper> getAllDataStore(@RequestParam(name = "limitData",required = true)Long limitData){
        List<MarketPlaceForStoreWrapper> thisOutput = new ArrayList();
        List<Long> storeIds = storeRepo.getLimitDataForMarketplace(limitData);
        List<MarketPlaceForItemWrapper> thisItem = getAllDataItem(limitData);
        int start = 0;
        List<Object[]> thisData = storeRepo.getAllDataForMarketplace(storeIds);
        for(Object[] obj: thisData){
            List<MarketPlaceForItemWrapper> itemsTemp = new ArrayList<>();
            MarketPlaceForStoreWrapper temp = new MarketPlaceForStoreWrapper();
            temp.setId(Long.parseLong(obj[0].toString()));
            temp.setName(obj[1].toString());
            if(obj[2]!=null)
            temp.setDesc(obj[2].toString());
            temp.setPhoneNumber(obj[3].toString());
            if(obj[4]!=null)
            temp.setImageUrl(obj[4].toString());
            for(int i=start;i<thisItem.size();i++){
                if(thisItem.get(i).getStoreId()==temp.getId()){
                    itemsTemp.add(thisItem.get(i));}
                else{
                    start=i;
                    break;}
            }
            temp.setItems(itemsTemp);
            thisOutput.add(temp);
        }
        return thisOutput;
    }
    
    
}
