package com.OnlineStore.OnlineStoreBackEnd;


import com.OnlineStore.OnlineStoreBackEnd.Admin.User.RoleRepository;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserRepository;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private RoleRepository repository;


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

    @Test
    public void testSeadfgfrchUsers(){
        User a=new User(	"joe@email.net"	,	" default2020",	"Joe"	,		"Sixpack"	)	;
        User 		bz	=	userRepository.save(	a	);
        assertThat(	bz	.getId()).isGreaterThan(	0	);
    }


    @Test
    public void testGetUserByEmai2l(){
        String email = "joe@email.net";

        User user = userRepository.getUserByEmail(email);
        var x = repository.findById(1).get();
        var y = repository.findById(2).get();
        user.addRole(x);
        user.addRole(y);
        userRepository.save(user);




    }


    @Test
    public void newusers(){

        User a=new User(	"joe@email.net"	,	"default2020",	"Joe"	,		"Sixpack"	)	;
        User 	b=new User(	"kanna.allada@gmail.com"	,	"default2020"	,	"Allada"	,	"Pavan"	)	;
        User	c=new User(	"aecllc.bnk@gmail.com,"	,	"default2020"	,	"Bruce"	,	"Kitchell"	)	;
        User	d=new User(	"muhammad.evran13@gmail.com"	,	"default2020"	,	"Muhammad"	,	"Evran"	)	;
        User	e=new User(	"kent.hervey8@gmail.com"	,	"default2020"	,	"Kent"	,	"Hervey"	)	;
        User	f=new User(	"alfredephraim26@gmail.com"	,	"default2020"	,	"Alfred"	,	"Ephraim"	)	;
        User	g=new User(	"nathihsa@gmail.com"	,	"default2020"	,	"Nathi"	,	"Sangweni"	)	;
        User	h=new User(	"ammanollashirisha2020@gmail.com"	,	"default2020"	,	"Ammanolla"	,	"Shirisha"	)	;
        User	i=new User(	"bfeeny238@hotmail.com"	,	"default2020"	,	"Bill"	,	"Feeny"	)	;
        User	j=new User(	"redsantosph@gmail.com"	,	"default2020"	,	"Frederick"	,	"delos Santos"	)	;
        User	k=new User(	"ro_anamaria@mail.ru"	,	"default2020"	,	"Ana"	,	"Maria"	)	;
        User	l=new User(	"maytassatya@hotmail.com"	,	"default2020"	,	"Satya"	,	"Narayana"	)	;
        User	m=new User(	"matthewefoli@gmail.com"	,	"default2020"	,	"Matthew"	,	"Efoli"	)	;
        User	n=new User(	"davekumara2@gmail.com"	,	"default2020"	,	"Dave"	,	"Kumar"	)	;
        User	o=new User(	"jackkbruce@yahoo.com"	,	"default2020"	,	"Jack"	,	"Bruce"	)	;
        User	p=new User(	"zirri.mohamed@gmail.com"	,	"default2020"	,	"Mohamed"	,	"Zirri"	)	;
        User	q=new User(	"mk.majumdar009@hotmail.com"	,	"default2020"	,	"Mithun"	,	"Kumar Majumdar"	)	;
        User	r=new User(	"aliraza.arain.28@gmail.com"	,	"default2020"	,	"Ali"	,	"Raza"	)	;
        User	s=new User(	"saachenry2877@gmail.com"	,	"default2020"	,	"Isaac"	,	"Henry"	)	;
        User	t=new User(	"s.stasovska82@hotmail.com"	,	"default2020"	,	"Svetlana"	,	"Stasovska"	)	;
        User	u=new User(	"mikegates2012@gmail.com"	,	"default2020"	,	"Mike"	,	"Gates"	)	;
        User	v=new User(	"pedroquintero67@gmail.com"	,	"default2020"	,	"Pedro"	,	"Quintero"	)	;
        User	w=new User(	"amina.elshal2@yahoo.com"	,	"default2020"	,	"Amina"	,	"Elshal"	)	;




        User 		bz	=	userRepository.save(	b	);
        User		cz	=	userRepository.save(	c	);
        User		dz	=	userRepository.save(	d	);
        User		ez	=	userRepository.save(	e	);
        User		fz	=	userRepository.save(	f	);
        User		gz	=	userRepository.save(	g	);
        User		hz	=	userRepository.save(	h	);
        User		iz	=	userRepository.save(	i	);
        User		jz	=	userRepository.save(	j	);
        User		kz	=	userRepository.save(	k	);
        User		lz	=	userRepository.save(	l	);
        User		mz	=	userRepository.save(	m	);
        User		nz	=	userRepository.save(	n	);
        User		oz	=	userRepository.save(	o	);
        User		pz	=	userRepository.save(	p	);
        User		qz	=	userRepository.save(	q	);
        User		rz	=	userRepository.save(	r	);
        User		sz	=	userRepository.save(	s	);
        User		tz	=	userRepository.save(	t	);
        User		uz	=	userRepository.save(	u	);
        User		vz	=	userRepository.save(	v	);
        User		wz	=	userRepository.save(	w	);







        assertThat(	bz	.getId()).isGreaterThan(	1	);
        assertThat(	cz	.getId()).isGreaterThan(	2	);
        assertThat(	dz	.getId()).isGreaterThan(	3	);
        assertThat(	ez	.getId()).isGreaterThan(	4	);
        assertThat(	fz	.getId()).isGreaterThan(	5	);
        assertThat(	gz	.getId()).isGreaterThan(	6	);
        assertThat(	hz	.getId()).isGreaterThan(	7	);
        assertThat(	iz	.getId()).isGreaterThan(	8	);
        assertThat(	jz	.getId()).isGreaterThan(	9	);
        assertThat(	kz	.getId()).isGreaterThan(	10	);
        assertThat(	lz	.getId()).isGreaterThan(	11	);
        assertThat(	mz	.getId()).isGreaterThan(	12	);
        assertThat(	nz	.getId()).isGreaterThan(	13	);
        assertThat(	oz	.getId()).isGreaterThan(	14	);
        assertThat(	pz	.getId()).isGreaterThan(	15	);
        assertThat(	qz	.getId()).isGreaterThan(	16	);
        assertThat(	rz	.getId()).isGreaterThan(	17	);
        assertThat(	sz	.getId()).isGreaterThan(	18	);
        assertThat(	tz	.getId()).isGreaterThan(	19	);
        assertThat(	uz	.getId()).isGreaterThan(	20	);
        assertThat(	vz	.getId()).isGreaterThan(	21	);
        assertThat(	wz	.getId()).isGreaterThan(	22	);



    }



}
