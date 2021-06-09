/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Commission;
import com.ThesisProject.models.Peripheral;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LENOVO
 */
@Repository
public interface CommissionRepositories extends JpaRepository<Commission, Long> {
    Commission getByPeripheral(Peripheral peripheral);
    
    @Query(value="select s.id as storeId, s.name as storeName, sum(p.click_counter) as clickCounter, sum(c.total_commission_amount) as totCommAm, sum(c.total_transaction) as totTransac, p.peripheral_link as pLink from mst_peripheral as p " +
        "join mst_item as i on p.item_id = i.id " +
        "join mst_store as s on s.id = i.store_id " +
        "join trx_commission as c on c.peripheral_id = p.id " +
        "join mst_referral_code as r on r.id = p.referral_id " +
        "join mst_promoter as pr on pr.id = r.promoter_id " +
        "where pr.id = :promoterId " +
        "group by s.id, pLink", nativeQuery = true)
    List<Object[]>getDataForSummary(@Param("promoterId")Long promoterId);
    
    @Query(value="select s.id as storeId, s.name as storeName, sum(p.click_counter) as clickCounter, sum(cd.commission_amount) as totCommAm, count(cd.id) as totTransac, p.peripheral_link as pLink from mst_peripheral as p " +
        "join mst_item as i on p.item_id = i.id " +
        "join mst_store as s on s.id = i.store_id " +
        "join trx_commission as c on c.peripheral_id = p.id " +
        "join trx_commission_detail as cd on cd.commission_id = c.id "+
        "join mst_referral_code as r on r.id = p.referral_id " +
        "join mst_promoter as pr on pr.id = r.promoter_id " +
        "where pr.id = :promoterId and cd.transaction_date between TO_DATE(:startDate,'yyyy-MM-dd') and TO_DATE(:endDate,'yyyy-MM-dd')  " +
        "group by s.id, pLink", nativeQuery = true)
    List<Object[]>getDataForSummaryByDate(@Param("promoterId")Long promoterId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Query(value="select s.id as storeId, pr.name as promoterName, sum(p.click_counter) as clickCounter, sum(c.total_commission_amount) as totCommAm, sum(c.total_transaction) as totTransac, p.peripheral_link as pLink from mst_peripheral as p " +
"        join mst_item as i on p.item_id = i.id " +
"        join mst_store as s on s.id = i.store_id " +
"        join trx_commission as c on c.peripheral_id = p.id " +
"        join mst_referral_code as r on r.id = p.referral_id " +
"        join mst_promoter as pr on pr.id = r.promoter_id " +
"        where s.id = :storeId " +
"        group by s.id, pr.id, pLink", nativeQuery = true)
    List<Object[]>getDataSummaryForStore(@Param("storeId")Long storeId);
    
    @Query(value="select s.id as storeId, pr.name as promoterName, sum(p.click_counter) as clickCounter, sum(cd.commission_amount) as totCommAm, count(cd.id) as totTransac, p.peripheral_link as pLink from mst_peripheral as p " +
"        join mst_item as i on p.item_id = i.id " +
"        join mst_store as s on s.id = i.store_id " +
"        join trx_commission as c on c.peripheral_id = p.id " +
"        join trx_commission_detail as cd on cd.commission_id = c.id "+
"        join mst_referral_code as r on r.id = p.referral_id " +
"        join mst_promoter as pr on pr.id = r.promoter_id " +
"        where s.id = :storeId and cd.transaction_date between :startDate and :endDate " +
"        group by s.id, pr.id,pLink", nativeQuery = true)
    List<Object[]>getDataSummaryForStoreByDate(@Param("storeId")Long storeId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
