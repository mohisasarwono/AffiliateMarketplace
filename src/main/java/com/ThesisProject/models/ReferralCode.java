/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "mst_referral_code")
public class ReferralCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referralBySystem, referralByUser;
    private Byte status;
    @OneToOne
    @JoinColumn(name = "promoter_id", referencedColumnName = "id")
    Promoter promoter;

    public ReferralCode() {
    }

    public ReferralCode(String referralBySystem, Byte status, Promoter promoter) {
        this.referralBySystem = referralBySystem;
        this.status = status;
        this.promoter = promoter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferralBySystem() {
        return referralBySystem;
    }

    public void setReferralBySystem(String referralBySystem) {
        this.referralBySystem = referralBySystem;
    }

    public String getReferralByUser() {
        return referralByUser;
    }

    public void setReferralByUser(String referralByUser) {
        this.referralByUser = referralByUser;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Promoter getPromoter() {
        return promoter;
    }

    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }
}
