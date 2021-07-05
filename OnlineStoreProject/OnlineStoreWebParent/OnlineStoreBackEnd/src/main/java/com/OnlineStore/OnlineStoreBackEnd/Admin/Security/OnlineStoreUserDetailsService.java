package com.OnlineStore.OnlineStoreBackEnd.Admin.Security;

import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserRepository;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OnlineStoreUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user != null){
            return new OnlineStoreUserDetails(user);
        }
        throw new UsernameNotFoundException("could not find user with email:" + email);
    }


}
