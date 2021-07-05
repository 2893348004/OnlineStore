package com.OnlineStore.OnlineStoreFrontEnd.Orders;

import com.OnlineStore.OnlineStoreCommon.Entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface OrdersRepository extends CrudRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o WHERE o.customerId = :customerId")
    public Orders findByCustomerId(Integer customerId);


}
