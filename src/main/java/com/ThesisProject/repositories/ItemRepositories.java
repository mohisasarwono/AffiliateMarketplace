/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.Item;
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
public interface ItemRepositories extends JpaRepository<Item, Long> {
    
    @Query(value="select id from mst_item limit :limitData",nativeQuery = true)
    List<Long> getLimitDataForMarketplace(@Param("limitData") Long limitData);
    
    @Query(value = "select * from mst_item where store_id = :storeId and status = 1 order by id",nativeQuery = true)
    List<Item> getByStoreAndStatus(@Param("storeId")Long storeId);
    
    @Query(value = "select * from mst_item order by id limit 100",nativeQuery = true)
    List<Item> getAllLimit();
    
    @Query(value = "select * from mst_item where id =:id",nativeQuery = true)
    Item getItemById(@Param("id")Long id);
    
    @Query(value = "select i.id as iId, i.name as iName, i.commission_price_or_percentage as iComPriOrPer, i.commission_status as iComStat, " +
"	i.expired_date as iExpDate, i.store_id as iStoreId, coalesce(count(cd.id),0) as totalTransc, i.description as iDesc, coalesce(sum(p.click_counter),0) as totalView, i.price as price, i.type as type, coalesce(i.photo_url,'null') as IPuRL, coalesce(i.recurring,0) as IRec " +
"from mst_item as i " +
"left join mst_peripheral as p on p.item_id = i.id " +
"left join trx_commission as c on c.peripheral_id = p.id " +
"left join trx_commission_detail as cd on cd.commission_id = c.id " +
"where i.id in (:itemIds) and i.qty > 0 " +
"group by iId, p.item_id order by i.store_id",nativeQuery=true)
    List<Object[]> getDataForMarketplace(@Param("itemIds")List<Long> itemIds);
    
}
