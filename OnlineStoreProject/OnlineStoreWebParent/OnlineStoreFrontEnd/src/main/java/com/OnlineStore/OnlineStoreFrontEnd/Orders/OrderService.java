package com.OnlineStore.OnlineStoreFrontEnd.Orders;


import com.OnlineStore.OnlineStoreCommon.Entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {


    @Autowired
    private OrdersRepository ordersRepository;

    public int saveThisOrderReturnOrderId(Integer customerId, LocalDateTime time, String items){
        Orders order = new Orders(customerId, time, items);
        ordersRepository.save(order);
        int orderId = order.getId();

        return  orderId ;
    }

    public void save(Orders orders){
        ordersRepository.save(orders);
    }

}
