package com.OnlineStore.OnlineStoreFrontEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.OnlineStore.OnlineStoreCommon.Entity", "com.OnlineStore.OnlineStoreFrontEnd"})
public class OnlineStoreFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreFrontEndApplication.class, args);
	}

}
