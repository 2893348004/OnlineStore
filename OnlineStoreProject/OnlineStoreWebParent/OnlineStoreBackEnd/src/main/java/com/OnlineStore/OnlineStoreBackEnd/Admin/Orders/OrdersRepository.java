package com.OnlineStore.OnlineStoreBackEnd.Admin.Orders;

import com.OnlineStore.OnlineStoreCommon.Entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrdersRepository extends PagingAndSortingRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.customerId = :customerId")
    public Orders findByCustomerId(Integer customerId);

}
