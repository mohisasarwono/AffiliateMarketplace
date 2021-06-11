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
import com.ThesisProject.models.Promoter;
import com.ThesisProject.repositories.CommissionDetailRepositories;
import com.ThesisProject.repositories.CommissionRepositories;
import com.ThesisProject.repositories.ItemRepositories;
import com.ThesisProject.repositories.PeripheralRepositories;
import com.ThesisProject.repositories.PromoterRepositories;
import com.ThesisProject.services.UserServices;
import com.ThesisProject.wrappers.CommissionDetailWrapper;
import com.ThesisProject.wrappers.CommissionSummaryWrapper;
import com.ThesisProject.wrappers.CommissionWrapper;
import com.ThesisProject.wrappers.TranscMessageWrapper;
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
    CommissionDetailRepositories commissionDetRepo;
    
    @Autowired
    PeripheralRepositories peripheralRepo;
    
    @Autowired
    ItemRepositories itemRepo; 
    
    @Autowired
    PromoterRepositories promoterRepo;
    String message;
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    public TranscMessageWrapper addCommission(@RequestBody CommissionWrapper commissionData){
        TranscMessageWrapper transMessage = new TranscMessageWrapper();
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
        
        Integer recurringCounter=peripheral.getItem().getRecurring();
            System.out.println(recurringCounter);
        String[][] isRecurring = checkRecurring(commission.getId());
            System.out.println(isRecurring.length);
        if(isRecurring.length==0)
                System.out.println("Null Nih");
        for(CommissionDetailWrapper thisCommDet : commissionData.getCommissionDetails()){
            CommissionDetail newCommissionDetail = new CommissionDetail();
            newCommissionDetail.setPrice(thisCommDet.getPrice());
            newCommissionDetail.setQty(thisCommDet.getQty());
            newCommissionDetail.setCommissionAmount(calculateCommissionAmount(thisCommDet.getPrice(),thisCommDet.getQty(),peripheral.getItem().getId(),peripheral.getId()));
            newCommissionDetail.setCommission(commission);
            newCommissionDetail.setCustomerId(commissionData.getCustomerId());
            if(recurringCounter>0&&isRecurring.length==0)
                 newCommissionDetail.setRecurringCounter(1);
            else{
                if(isRecurring.length>0){
                    if(recurringCounter>Integer.parseInt(isRecurring[0][1])&&commissionData.getCustomerId().toString().equals(isRecurring[0][2]))
                        newCommissionDetail.setRecurringCounter(1);
                    else 
                        newCommissionDetail.setRecurringCounter(0);
                }}
            totalCommissionAmount += newCommissionDetail.getCommissionAmount();
            commissionDetRepo.save(newCommissionDetail);
        }
        commission.setTotalCommissionAmount(commission.getTotalCommissionAmount()+totalCommissionAmount);
        commission.setTotalTransaction(commission.getTotalTransaction()+commissionData.getCommissionDetails().size());
        commissionRepo.save(commission);
        transMessage.setMessage("Transaction Successed");
        transMessage.setCode("SUC");
        transMessage.setStatus(true);
        }catch(Exception e){
            e.printStackTrace();
            transMessage.setMessage("Transaction Failed");
            transMessage.setCode("FL");
            transMessage.setStatus(false);
        }
        return transMessage;
    }
    
    public Double calculateCommissionAmount(Double price, Double qty, Long itemId, Long peripheralId){
        Double commissionAmount = 0.0;
        Item thisItem = itemRepo.getOne(itemId);
        if(thisItem.getCommissionStatus()==1){
            commissionAmount = thisItem.getCommissionPriceOrPercentage()*qty;
        }else{
            commissionAmount = thisItem.getCommissionPriceOrPercentage()*price;
        }
        Promoter thisPromoter = promoterRepo.findByPeripheral(peripheralId);
        thisPromoter.setCommissionMoney(thisPromoter.getCommissionMoney()+commissionAmount);
        promoterRepo.save(thisPromoter);
        return commissionAmount;
    }
    
    @RequestMapping(value="getCommissionSummary",method = RequestMethod.GET)
    public List<CommissionSummaryWrapper> getCommissionSummary(@RequestParam(name="promoterId",required = false, defaultValue = "0")Long promoterId,
            @RequestParam(name = "storeId", required = false, defaultValue = "0") Long storeId, @RequestParam(name = "startDate", required = false, defaultValue = "null")String startDate,@RequestParam(name = "endDate", required = false, defaultValue = "null")String endDate){
        try{
        List<CommissionSummaryWrapper> thisOutput = new ArrayList();
        List<Object[]> thisDatas = new ArrayList();
        if(promoterId>0){
            if(!startDate.equals("null")&&!endDate.equals("null"))
                thisDatas=commissionRepo.getDataForSummaryByDate(promoterId,startDate,endDate);
            else
                thisDatas=commissionRepo.getDataForSummary(promoterId);
        }
        else if(storeId>0){
            if(!startDate.equals("null")&&!endDate.equals("null"))
                thisDatas=commissionRepo.getDataSummaryForStore(storeId);    
            else
                thisDatas=commissionRepo.getDataSummaryForStore(storeId);
        }
        for(Object[] data:thisDatas){
            thisOutput.add(new CommissionSummaryWrapper(Long.parseLong(data[0].toString()), data[1].toString(), Integer.parseInt(data[2].toString()), Double.parseDouble(data[3].toString()),Double.parseDouble(data[4].toString()),data[5].toString()));
        }
        return thisOutput;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="getPromoterBalance",method = RequestMethod.GET)
    public Double getPromoterBalance(@RequestParam(name = "promoterId",required = true)Long promoterId){
        return promoterRepo.getOne(promoterId).getCommissionMoney();
    }
    
    @RequestMapping(value="drawMoney",method = RequestMethod.GET)
    public String drawMoney(@RequestParam(name = "promoterId",required = true)Long promoterId,@RequestParam(name = "balance",required = true)Double balance,@RequestParam(name="password",required = true)String password){
        Promoter promoter = promoterRepo.findByIdAndPassword(promoterId, password);
        if(promoter!=null){
        if(promoter.getCommissionMoney()>balance){
            promoter.setCommissionMoney(promoter.getCommissionMoney()-balance);
            promoterRepo.save(promoter);
            message="Success Draw Your Money";
        }else{
            message="Error : Your input value is greater than your balance";
        }}else{message="Error : Password isn't correct"; }
        return message;
    }
    
//    @RequestMapping(value="checkRecurring",method = RequestMethod.GET)
    public String[][] checkRecurring(@RequestParam(name = "commissionId")Long commissionId){
        Integer recurring = commissionRepo.getOne(commissionId).getPeripheral().getItem().getRecurring();
        String[][] output = commissionDetRepo.checkIsTheFirstCustomerRecurring(commissionId);
        if(output!=null){
            if(Integer.parseInt(output[0][1])>recurring)
                return new String[1][1];
            return output;}
        return new String[1][1];
    }
    
    @RequestMapping(value="checkRecurringByCustId",method = RequestMethod.GET)
    public String checkRecurringByCustId(@RequestParam(name = "customerId")Long customerId, @RequestParam(name = "itemId")Long itemId){
        String[][] output = commissionDetRepo.checkIsTheFirstCustomerRecurringByCustId(customerId,itemId);
           System.out.println(output.length);
        if(output.length>0){
            System.out.println("A");
            for(int i=0;i<output.length;i++){
            if(Integer.parseInt(output[i][1])<Integer.parseInt(output[i][2]))
                return output[i][3];
            }
        }
        return "null";
    }
    
}
