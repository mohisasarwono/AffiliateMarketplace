/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ThesisProject.repositories;

import com.ThesisProject.models.CommissionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author LENOVO
 */
public interface CommissionDetailRepositories extends JpaRepository<CommissionDetail, Long> {
    @Query(value="select p.peripheral_link, cast(count(cd.id) as varchar(255)) as recCounter,cast(p.id as varchar(255)) as pId from trx_commission_detail as cd " +
                 "join trx_commission as c on cd.commission_id = c.id " +
                 "join mst_peripheral as p on p.id = c.peripheral_id " +
                 "where c.id = :commId and cd.recurring_counter > 0 group by p.id", nativeQuery = true)
    String[][] checkIsTheFirstCustomerRecurring(@Param("commId")Long commId);
}
