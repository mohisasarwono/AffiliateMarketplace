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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "trx_commission")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalCommissionAmount, totalTransaction;
    
    
    @ManyToOne
    @JoinColumn(name = "peripheral_id",referencedColumnName = "id",updatable = true)
    private Peripheral peripheral;

    public Commission() {
    }

    public Commission(Date transactionDate, Double commissionAmount, Peripheral peripheral) {
        this.totalCommissionAmount = commissionAmount;
        this.peripheral = peripheral;
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

    public void setTotalCommissionAmount(Double commissionAmount) {
        this.totalCommissionAmount = commissionAmount;
    }

    public Peripheral getPeripheral() {
        return peripheral;
    }

    public void setPeripheral(Peripheral peripheral) {
        this.peripheral = peripheral;
    }
    
}
