/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Store;
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
public interface StoreRepositories extends JpaRepository<Store, Long> {
    Store findByEmailAndPassword(String email, String password);
    
    @Query(value="select id from mst_store limit :limitData",nativeQuery = true)
    List<Long> getLimitDataForMarketplace(@Param("limitData") Long limitData);
    
    @Query(value="select id, name,store_description, phone_number, photo_profile_url from mst_store where id in (:storeIds) and status = 1 order by id", nativeQuery = true)
    List<Object[]> getAllDataForMarketplace(@Param("storeIds") List<Long> storeIds);
    
    @Query(value="select i.id as iId from mst_store as s "
            + "join mst_item as i on s.id = i.store_id where s.name ilike(concat('%',:name,'%')) and i.status = 1 and s.status = 1 order by i.id ", nativeQuery = true)
    List<Long> getItemByNStoreName(@Param("name")String name);
    
    @Query(value="select i.id as iId from mst_store as s "
            + "join mst_item as i on s.id = i.store_id where i.name ilike(concat('%',:name,'%')) and i.status = 1 order by i.id ", nativeQuery = true)
    List<Long> getItemByItemName(@Param("name")String name);
    
    @Query(value="select i.id as iId from mst_store as s "
            + "join mst_item as i on s.id = i.store_id  where s.type = :type and  s.status = 1 and i.status = 1 order by i.id", nativeQuery = true )
    List<Long> getItemByType(@Param("type")Integer type);
    
    @Query(value="select i.id from mst_store as s " +
    "join mst_item as i on s.id = i.store_id " +
    "join mst_peripheral as p on p.item_id = i.id " +
    "join trx_commission as c on c.peripheral_id = p.id " +
    "where c.total_transaction >= :totalTranscation and s.status = 1 order by i.id ", nativeQuery = true)
    List<Long> getItemByTotalTransaction(@Param("totalTranscation")Integer totalTransaction);
    
     @Query(value="select id from mst_item where commission_price_or_percentage>:commissionRange and commission_status =:commissionStatus and status= 1 order by id ", nativeQuery = true)
    List<Long> getItemByCommissionRange (@Param("commissionRange")Double commissionRange, @Param("commissionStatus") Byte comStat);
}
