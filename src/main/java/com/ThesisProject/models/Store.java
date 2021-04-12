/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "mst_store")
public class Store extends User{
    private String storeDescription;

    public Store() {
    }

    public Store(String name, String address, String phoneNumber, String photoProfileUrl,  String email, String password, Byte status,String storeDescription) {
        super(name, address, phoneNumber, photoProfileUrl, email,password,status);
        this.storeDescription = storeDescription;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }
    
    
    
}
