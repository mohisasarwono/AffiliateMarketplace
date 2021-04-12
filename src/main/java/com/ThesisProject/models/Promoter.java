/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "mst_promoter")
public class Promoter extends User {
    private Date DoB;
    public Promoter() {
    }

    public Promoter(String name, String address, String phoneNumber, String photoProfileUrl, String email, String password, Byte status) {
        super(name, address, phoneNumber, photoProfileUrl, email, password, status);
    }
    
}
