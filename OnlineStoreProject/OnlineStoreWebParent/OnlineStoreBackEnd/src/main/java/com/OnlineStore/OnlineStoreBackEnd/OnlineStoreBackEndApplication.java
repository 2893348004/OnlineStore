package com.OnlineStore.OnlineStoreBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.OnlineStore.OnlineStoreCommon.Entity", "com.OnlineStore.OnlineStoreBackEnd.Admin.User"})
public class OnlineStoreBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreBackEndApplication.class, args);
	}

}
