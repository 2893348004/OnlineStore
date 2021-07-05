package com.OnlineStore.OnlineStoreCommon.Entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="customerId", nullable = false)
    private Integer customerId;

    @Column(name = "created_time", length = 15, nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "item_list", nullable = false)
    private String items;




    public Orders() {
    }

    public Orders(Integer customerId, LocalDateTime createdTime, String items) {
        this.customerId = customerId;
        this.createdTime = createdTime;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
