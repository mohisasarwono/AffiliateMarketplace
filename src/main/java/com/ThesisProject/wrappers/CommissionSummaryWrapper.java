/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.wrappers;

/**
 *
 * @author LENOVO
 */
public class CommissionSummaryWrapper {
    private Long storeId;
    private String storeOrPromoterName,peripheralLink,recurringCounter;
    private Integer clickCounter;
    private Double totalCommissionAmount, totalTransaction;

    public CommissionSummaryWrapper() {
    }

    public CommissionSummaryWrapper(Long storeId, String storeOrPromoterName, Integer clickCounter, Double totalCommissionAmount, Double totalTransaction,String peripheralLink, String recurringCounter) {
        this.storeId = storeId;
        this.storeOrPromoterName = storeOrPromoterName;
        this.clickCounter = clickCounter;
        this.totalCommissionAmount = totalCommissionAmount;
        this.totalTransaction = totalTransaction;
        this.peripheralLink=peripheralLink;
        this.recurringCounter=recurringCounter;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreOrPromoterName() {
        return storeOrPromoterName;
    }

    public void setStoreOrPromoterName(String storeOrPromoterName) {
        this.storeOrPromoterName = storeOrPromoterName;
    }

    public Integer getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(Integer clickCounter) {
        this.clickCounter = clickCounter;
    }

    public Double getTotalCommissionAmount() {
        return totalCommissionAmount;
    }

    public void setTotalCommissionAmount(Double totalCommissionAmount) {
        this.totalCommissionAmount = totalCommissionAmount;
    }

    public Double getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public String getPeripheralLink() {
        return peripheralLink;
    }

    public void setPeripheralLink(String peripheralLink) {
        this.peripheralLink = peripheralLink;
    }

    public String getReccurringCounter() {
        return recurringCounter;
    }

    public void setReccurringCounter(String recurringCounter) {
        this.recurringCounter = recurringCounter;
    }
}
