/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.wrappers;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class ItemWrapper {
    private Long id, storeId;
    private String name, description;
    private Double price,commissionPriceOrPercentage;
    private Byte status, commissionStatus;
    private String expiredDate;

    public ItemWrapper() {
    }

    public ItemWrapper(Long storeId, String name, String description, Double price, Double commissionPriceOrPercentage, Byte status, Byte commissionStatus, String expiredDate) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.commissionPriceOrPercentage = commissionPriceOrPercentage;
        this.status = status;
        this.commissionStatus = commissionStatus;
        this.expiredDate = expiredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCommissionPriceOrPercentage() {
        return commissionPriceOrPercentage;
    }

    public void setCommissionPriceOrPercentage(Double commissionPriceOrPercentage) {
        this.commissionPriceOrPercentage = commissionPriceOrPercentage;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getCommissionStatus() {
        return commissionStatus;
    }

    public void setCommissionStatus(Byte commissionStatus) {
        this.commissionStatus = commissionStatus;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    
}
