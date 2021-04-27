/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.wrappers;

import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CommissionWrapper {
    private Long id;
    private String peripheralLink;
    private Double totalCommissionAmount, totalTransaction;
    private List<CommissionDetailWrapper> commissionDetails;

    public CommissionWrapper() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<CommissionDetailWrapper> getCommissionDetails() {
        return commissionDetails;
    }

    public void setCommissionDetails(List<CommissionDetailWrapper> commissionDetails) {
        this.commissionDetails = commissionDetails;
    }

    public String getPeripheralLink() {
        return peripheralLink;
    }

    public void setPeripheralLink(String peripheralLink) {
        this.peripheralLink = peripheralLink;
    }
    
}
