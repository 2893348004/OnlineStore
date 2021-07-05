package com.OnlineStore.OnlineStoreBackEnd;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLOutput;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordEncryptionTests {

    @Test
    public void testEncodePassword(){

        BCryptPasswordEncoder bCryptPasswordEncoder;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "testing1995";
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);


        System.out.println(encodedPassword);

        boolean matches = bCryptPasswordEncoder.matches(rawPassword,encodedPassword);

        assertThat(matches).isTrue();
    }

}
