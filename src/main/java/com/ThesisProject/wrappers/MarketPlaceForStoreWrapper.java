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
public class MarketPlaceForStoreWrapper{
    private Long id;
    private String name,desc, imageUrl;
    private String phoneNumber;
    private List<MarketPlaceForItemWrapper> items;
    public MarketPlaceForStoreWrapper() {
    }
    
    public MarketPlaceForStoreWrapper(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<MarketPlaceForItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<MarketPlaceForItemWrapper> items) {
        this.items = items;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
}
