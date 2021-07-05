package com.OnlineStore.OnlineStoreBackEnd.Admin.User;


import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import com.OnlineStore.OnlineStoreCommon.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    public static final int USERS_PER_PAGE = 4;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getByEmail(String email){
        return userRepository.getUserByEmail(email);

    }


    public List<User> listAll(){

        return (List<User>) userRepository.findAll();
    }

    public Page<User> listByPage(int pageNumber,
                                 String sortField,
                                 String sortDirection,
                                 String keyword){
       Sort sort = Sort.by(sortField);
       sort = sortDirection.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber -1 ,USERS_PER_PAGE, sort );

       if(keyword != null){
          // keyword = keyword.toLowerCase();
           return userRepository.findAllIgnoreCase(keyword,pageable);
       }

        return userRepository.findAll(pageable);
    }

    public List<Role> listRoles(){

        return ( List<Role> ) roleRepository.findAll();
    }



    public User save(User user){
        boolean isUpdatingUser = (user.getId() != null);

            if(isUpdatingUser){
                User existingUser = userRepository.findById(user.getId()).get();
                if (user.getPassword().isEmpty()) {
                    user.setPassword(existingUser.getPassword());
                }else{
                    encodePassword(user);
                }
                if (user.getPhoto() == null) {
                    user.setPhoto(existingUser.getPhoto());
                }
                if (user.getEnabled() == null) {
                    user.setEnabled(existingUser.getEnabled());
                }


            }else{
                encodePassword(user);
            }

       return userRepository.save(user);
    }

    public User updateUserAccount(User userInForm){
        User userInDB = userRepository.findById(userInForm.getId()).get();


        if(!userInForm.getPassword().isEmpty()) {
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }
        if(userInForm.getPhoto() != null ) {
            userInDB.setPhoto(userInForm.getPhoto());
        }

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepository.save(userInDB);
    }




    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNewUserMode = (id == null);

            if(isCreatingNewUserMode){
                if(userByEmail != null) return false;
            } else {
                if(userByEmail.getId() != id) return false;
                }


        return true;
        }






    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("User with " + id + " as ID was not found" );
        }
    }


    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);

        if(countById == null || countById == 0){
            throw new UserNotFoundException("Could not find any user with ID " + id) ;
        }
        userRepository.deleteById(id);
    }






}
