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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        List<Long> storeIds = storeRepo.getLimitDataForMarketplace(limitData);
        return initDataItem(storeIds);
    }
    
    @RequestMapping(value="getAllDataStore", method=RequestMethod.GET)
    public List<MarketPlaceForStoreWrapper> getAllDataStore(@RequestParam(name = "limitData",required = true)Long limitData){
        List<Long> storeIds = storeRepo.getLimitDataForMarketplace(limitData);
        List<MarketPlaceForItemWrapper> thisItem = getAllDataItem(limitData);
        return initDataStore(storeIds, thisItem);
    }
    
    @RequestMapping(value="advanceSearch", method=RequestMethod.GET)
    public List<MarketPlaceForItemWrapper> advanceSearch(@RequestParam(name = "storeName",required = false,defaultValue = "null")String storeName,@RequestParam(name = "totalTransaction",required = false, defaultValue = "0")Integer totalTransac, 
            @RequestParam(name = "type",required = false, defaultValue = "null")String type, @RequestParam(name="itemName",required = false,defaultValue = "null")String itemName,
            @RequestParam(name = "commissionRange",required = false, defaultValue = "0.0")Double commissionRange,@RequestParam(name = "commissionStatus",required = false, defaultValue = "0")Byte commissionStatus){
        List<Long> itemIds = new ArrayList();
        int param=0;
        if(!storeName.equals("null")){
            itemIds.addAll(storeRepo.getItemByNStoreName(storeName));
            param++;
        }
        if(totalTransac>0){
            itemIds.addAll(storeRepo.getItemByTotalTransaction(totalTransac));
            param++;
        }
        if(!type.equals("null")){
            itemIds.addAll(storeRepo.getItemByType(type));
            param++;
        }
        if(!itemName.equals("null")){   
            itemIds.addAll(storeRepo.getItemByItemName(itemName));
            param++;
        }
        if(commissionRange > 0.0 && !commissionStatus.toString().equals("0")){
            itemIds.addAll(storeRepo.getItemByCommissionRange(commissionRange, commissionStatus));
            param++;
        }
        
        if(itemIds==null)
            return getAllDataItem((long)100);
        
        if(param>1){
            List<Long> resultDuplicate = findDuplicate(itemIds,param);
            for(Long dat : resultDuplicate){
                System.out.println("id Dup : "+ dat);
               }
            return initDataItem(resultDuplicate);
        }
        
        return initDataItem(itemIds);
        
    }
    
     public List<Long> findDuplicate(List<Long> thisItemIds, Integer param)
    {
        return thisItemIds.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(),
                    Collectors.counting()))
            .entrySet()
            .stream()
            .filter(m -> m.getValue() > (param-1))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
     public List<MarketPlaceForStoreWrapper> initDataStore(List<Long> storeIds, List<MarketPlaceForItemWrapper> thisItem){
        List<MarketPlaceForStoreWrapper> thisOutput = new ArrayList();
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
    
      public List<MarketPlaceForItemWrapper> initDataItem(List<Long> itemIds){
        List<MarketPlaceForItemWrapper> thisOutput = new ArrayList();
        List<Object[]> thisData = itemRepo.getDataForMarketplace(itemIds);
        for(Object[] obj: thisData){
            thisOutput.add(new MarketPlaceForItemWrapper(Long.parseLong(obj[0].toString()), obj[1].toString(), obj[7].toString(), "imageUrl", Double.parseDouble(obj[2].toString()),Integer.parseInt(obj[6].toString()), obj[4].toString() , Long.parseLong(obj[5].toString()),Byte.parseByte(obj[3].toString()),Integer.parseInt(obj[8].toString()), Double.parseDouble(obj[9].toString()),obj[10].toString()));
        }
        return thisOutput;
      }
}
