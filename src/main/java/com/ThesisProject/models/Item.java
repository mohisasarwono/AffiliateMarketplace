/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "mst_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, description;
    private Double price,commissionPriceOrPercentage, qty;
    private Byte status, commissionStatus;
    private Date expiredDate;
    private Integer recurring;
    
    @ManyToOne
    @JoinColumn(name = "store_id",referencedColumnName = "id",updatable = true, nullable = false)
    private Store store;

    public Item() {
    }

    public Item(String name, Double price, Double commissionPriceOrPercentage, Store store) {
        this.name = name;
        this.price = price;
        this.commissionPriceOrPercentage = commissionPriceOrPercentage;
        this.store = store;
    }

    public Item(String name,Double price, Double commissionPriceOrPercentage, Byte status, Byte commissionStatus, Date expiredDate, Store store) {
        this.name = name;
        this.price = price;
        this.commissionPriceOrPercentage = commissionPriceOrPercentage;
        this.status = status;
        this.commissionStatus = commissionStatus;
        this.expiredDate = expiredDate;
        this.store = store;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getRecurring() {
        return recurring;
    }

    public void setRecurring(Integer recurring) {
        this.recurring = recurring;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }
    
    
}
