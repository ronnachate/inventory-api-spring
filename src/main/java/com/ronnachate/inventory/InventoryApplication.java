package com.ronnachate.inventory;

import com.ronnachate.inventory.user.entity.UserStatus;
import com.ronnachate.inventory.user.repository.UserStatusRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Autowired
	UserStatusRepository userStatusRepository;

	@Bean
	@ConditionalOnProperty(prefix = "app", name = "db.seed.enabled", havingValue = "true")
	public CommandLineRunner seedRequiredData() {
		return args -> {
			if (userStatusRepository.count() == 0) {
				List<UserStatus> userStatuses = Arrays.asList(
						new UserStatus(1, "Active"),
						new UserStatus(2, "Inactive"),
						new UserStatus(3, "Deleted"));

				userStatusRepository.saveAll(userStatuses);
			}
		};
	}
}
