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
    private String storeName;
    private Integer clickCounter;
    private Double totalCommissionAmount, totalTransaction;

    public CommissionSummaryWrapper() {
    }

    public CommissionSummaryWrapper(Long storeId, String storeName, Integer clickCounter, Double totalCommissionAmount, Double totalTransaction) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.clickCounter = clickCounter;
        this.totalCommissionAmount = totalCommissionAmount;
        this.totalTransaction = totalTransaction;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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
    
    
}
