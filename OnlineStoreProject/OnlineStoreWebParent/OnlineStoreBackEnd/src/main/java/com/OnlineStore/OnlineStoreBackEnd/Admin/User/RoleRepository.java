package com.OnlineStore.OnlineStoreBackEnd.Admin.User;


import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository <Role,Integer>{


}
