package com.OnlineStore.OnlineStoreBackEnd;

import com.OnlineStore.OnlineStoreBackEnd.Admin.User.RoleRepository;
import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repository;

    @Test
    public void testCreateFirstRole(){
     Role roleAdmin = new Role("Admin", "manages everything dealing with computers for company");
      Role savedRole = repository.save(roleAdmin);


       assertThat(savedRole.getId()).isGreaterThan(0);

      }

    @Test
    public void testCreateRestOfRole(){
        Role roleSales = new Role("Sales", "arranges the transaction of services");
        Role Editor = new Role("Editor", "manage categories, brands, products, articles and menus");
        Role roleShipping = new Role("Shipping", "arranges the logistics of providing  services");
        Role roleAssistant = new Role("Assistant ", "helps with general customer issues");

        repository.saveAll(List.of(roleSales,Editor,roleShipping,roleAssistant));

    }



}
