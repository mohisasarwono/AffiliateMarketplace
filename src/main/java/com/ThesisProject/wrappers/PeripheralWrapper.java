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
public class PeripheralWrapper {
    private Long id;
    private String peripheralLink;
    private Integer clickCounter;
    private String duration;
    private Byte status;
    private Integer totalTransaction;

    public PeripheralWrapper() {
    }

    public PeripheralWrapper(Long id, String peripheralLink, Integer clickCounter, String duration, Byte status, Integer totalTransaction) {
        this.id = id;
        this.peripheralLink = peripheralLink;
        this.clickCounter = clickCounter;
        this.duration = duration;
        this.status = status;
        this.totalTransaction = totalTransaction;
    }

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Integer totalTransaction) {
        this.totalTransaction = totalTransaction;
    }
    
    
}
