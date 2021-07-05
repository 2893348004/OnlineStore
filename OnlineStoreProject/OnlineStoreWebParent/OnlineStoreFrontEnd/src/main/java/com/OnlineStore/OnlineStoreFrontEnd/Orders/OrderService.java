package com.OnlineStore.OnlineStoreFrontEnd.Orders;


import com.OnlineStore.OnlineStoreCommon.Entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    @Autowired
    private OrdersRepository ordersRepository;

    public void save(Orders orders){

        ordersRepository.save(orders);

    }

}
