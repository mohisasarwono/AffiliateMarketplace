/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.models;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name="mst_peripheral")
public class Peripheral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String peripheralLink;
    private Integer clickCounter;
    private Date duration;
    private Byte status;

    public Peripheral() {
    }

    public Peripheral(String peripheralLink, Integer clickCounter, ReferralCode referralCode, Item item) {
        this.peripheralLink = peripheralLink;
        this.clickCounter = clickCounter;
        this.referralCode = referralCode;
        this.item = item;
    }
    
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "referral_id",referencedColumnName = "id")
    private ReferralCode referralCode;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id",referencedColumnName = "id")
    private Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeripheralLink() {
        return peripheralLink;
    }

    public void setPeripheralLink(String peripheralLink) {
        this.peripheralLink = peripheralLink;
    }

    public Integer getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(Integer clickCounter) {
        this.clickCounter = clickCounter;
    }

    public ReferralCode getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(ReferralCode referralCode) {
        this.referralCode = referralCode;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    
}
