/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.controllers;

import com.ThesisProject.models.Commission;
import com.ThesisProject.models.CommissionDetail;
import com.ThesisProject.models.Item;
import com.ThesisProject.models.Peripheral;
import com.ThesisProject.repositories.CommissionRepositories;
import com.ThesisProject.repositories.ItemRepositories;
import com.ThesisProject.repositories.PeripheralRepositories;
import com.ThesisProject.wrappers.CommissionDetailWrapper;
import com.ThesisProject.wrappers.CommissionSummaryWrapper;
import com.ThesisProject.wrappers.CommissionWrapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LENOVO
 */
@RestController
@RequestMapping(path = "apis/commission")
public class CommissionController {
    @Autowired
    CommissionRepositories commissionRepo;
    
    @Autowired
    PeripheralRepositories peripheralRepo;
    
    @Autowired
    ItemRepositories itemRepo; 
    
    String message;
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String addCommission(@RequestBody CommissionWrapper commissionData){
        Double totalCommissionAmount = 0.0;
        Peripheral peripheral=peripheralRepo.getByPeripheralLink(commissionData.getPeripheralLink());
        Commission commission = commissionRepo.getByPeripheral(peripheral);
        try{
        if(commission==null){
            commission = new Commission();
            commission.setPeripheral(peripheralRepo.getByPeripheralLink(commissionData.getPeripheralLink()));
            commission.setTotalCommissionAmount(0.0);
            commission.setTotalTransaction(0);
            commissionRepo.save(commission);
        }
        
        for(CommissionDetailWrapper thisCommDet : commissionData.getCommissionDetails()){
            CommissionDetail newCommissionDetail = new CommissionDetail();
            newCommissionDetail.setPrice(thisCommDet.getPrice());
            newCommissionDetail.setQty(thisCommDet.getQty());
            newCommissionDetail.setCommissionAmount(calculateCommissionAmount(thisCommDet.getPrice(),thisCommDet.getQty(),peripheral.getItem().getId()));
            newCommissionDetail.setCommission(commission);
            totalCommissionAmount += newCommissionDetail.getCommissionAmount();
        }
        commission.setTotalCommissionAmount(totalCommissionAmount);
        commission.setTotalTransaction(commissionData.getCommissionDetails().size());
        commissionRepo.save(commission);
        message = "Success adding commission";
        }catch(Exception e){
            e.printStackTrace();
            message="Error";
        }
        return message;
    }
    
    public Double calculateCommissionAmount(Double price, Double qty, Long itemId){
        Double commissionAmount = 0.0;
        Item thisItem = itemRepo.getOne(itemId);
        if(thisItem.getCommissionStatus()==1){
            commissionAmount = thisItem.getCommissionPriceOrPercentage()*qty;
        }else{
            commissionAmount = thisItem.getCommissionPriceOrPercentage()*price;
        }
        return commissionAmount;
    }
    
    @RequestMapping(value="getCommissionSummary",method = RequestMethod.GET)
    public List<CommissionSummaryWrapper> getCommissionSummary(@RequestParam(name="promoterId",required = true)Long promoterId){
        List<CommissionSummaryWrapper> thisOutput = new ArrayList();
        List<Object[]> thisDatas = commissionRepo.getDataForSummary(promoterId);
        
        for(Object[] data:thisDatas){
            thisOutput.add(new CommissionSummaryWrapper(Long.parseLong(data[0].toString()), data[1].toString(), Integer.parseInt(data[2].toString()), Double.parseDouble(data[3].toString()),Double.parseDouble(data[4].toString())));
        }
        return thisOutput;}
}
