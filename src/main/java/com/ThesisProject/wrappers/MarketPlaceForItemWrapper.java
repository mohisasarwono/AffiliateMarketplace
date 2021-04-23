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
public class MarketPlaceForItemWrapper{
    private Long id,storeId;
    private String name,desc, imageUrl;
    protected Double commision;
    private Integer totalTranscation;
    private String durationDate;
    private Byte commissionStat;

    public MarketPlaceForItemWrapper() {
    }

    public MarketPlaceForItemWrapper(Long id, String name, String desc, String imageUrl, Double commision, Integer totalTranscation, String durationDate, Long storeId, Byte commissionStat) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.commision = commision;
        this.totalTranscation = totalTranscation;
        this.durationDate = durationDate;
        this.storeId = storeId;
        this.commissionStat = commissionStat;
    }

    public Double getCommision() {
        return commision;
    }

    public void setCommision(Double commision) {
        this.commision = commision;
    }

    public Integer getTotalTranscation() {
        return totalTranscation;
    }

    public void setTotalTranscation(Integer totalTranscation) {
        this.totalTranscation = totalTranscation;
    }

    public String getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(String durationDate) {
        this.durationDate = durationDate;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Byte getCommissionStat() {
        return commissionStat;
    }

    public void setCommissionStat(Byte commissionStat) {
        this.commissionStat = commissionStat;
    }
    
    
    
    
}
