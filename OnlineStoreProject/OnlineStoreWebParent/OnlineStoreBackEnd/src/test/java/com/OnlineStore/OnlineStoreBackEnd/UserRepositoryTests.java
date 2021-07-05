package com.OnlineStore.OnlineStoreBackEnd;


import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserRepository;
import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreatingUser(){
        Role roleSales = entityManager.find(Role.class, 2);

        User userDavid = new User("davidsmith@hotmail.com", "david1991", "David", "Smith");
        userDavid.addRole(roleSales);

        User savedUser = userRepository.save(userDavid);
        assertThat(savedUser.getId()).isGreaterThan(0);


    }


    @Test
    public void testCreatingUserWithMultipleRoles(){


        User userVanessa = new User("vanessaroberts@hotmail.com", "vanessa1991", "Vanessa", "Roberts");

        Role roleShipping = new Role(2);
        Role roleAssistant = new Role(3);

        userVanessa.addRole(roleShipping);
        userVanessa.addRole(roleAssistant);

        User savedUser = userRepository.save(userVanessa);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }


    @Test
    public void updateUserInfo(){
        User userVanessa = userRepository.findById(2).get();
        userVanessa.setEnabled(true);

        userRepository.save(userVanessa);

        assertThat(userVanessa.getFirstName()).isEqualTo("Vanessa");
    }

    @Test
    public void resetAllPasswords(){

        BCryptPasswordEncoder bCryptPasswordEncoder;
        var users = userRepository.findAll();

        for (User user: users){

            bCryptPasswordEncoder = new BCryptPasswordEncoder();

            String rawPassword = "password2021";
            String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

            user.setPassword(encodedPassword);
            userRepository.save(user);

        }

    }


    @Test
    public void testGetUserByEmail(){
        String email = "abcdef@email.com";
        String email2 = "davidsmith@hotmail.com";
        User invalidUser = userRepository.getUserByEmail(email);

        assertThat(invalidUser).isNull();

    }

    @Test
    public void testGetUserCountById(){
        Integer id = 3;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testToggleDisableUser(){
        Integer id = 1;
        userRepository.updateEnabledStatus(id, true);
    }

    @Test
    public void getmydirectory(){
       // String dirName = "user-photos";
        String dirName = "category-images";
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        System.out.println(userPhotosPath);
        //System.out.println();

    }

    @Test
    public void testDisplayingFirstPage(){

        int pageNumber = 0;
        int pageSize = 4;


        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> listusers = page.getContent();

        listusers.forEach(user -> System.out.println(user) );

        assertThat(listusers.size()).isEqualTo(pageSize);

    }

    @Test
    public void testSearchUsers(){

        String keyword = "bruce";

        int pageNumber = 0;
        int pageSize = 4;


        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAllIgnoreCase(keyword, pageable);

        List<User> listusers = page.getContent();

        listusers.forEach(user -> System.out.println(user) );

        assertThat(listusers.size()).isGreaterThan(0);

    }





}
