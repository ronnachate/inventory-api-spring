package com.ronnachate.inventory;

import com.ronnachate.inventory.user.entity.UserStatus;
import com.ronnachate.inventory.user.repository.UserStatusRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@SpringBootApplication
public class InventoryApplication {

	private static final Logger logger = LogManager.getLogger(InventoryApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
        	.setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}

	@Autowired
	UserStatusRepository userStatusRepository;

	@Bean
	@ConditionalOnProperty(prefix = "app", name = "db.seed.enabled", havingValue = "true")
	public CommandLineRunner seedRequiredData() {
		return args -> {
			if (userStatusRepository.count() == 0) {
				logger.info("Seeding user status data");
				List<UserStatus> userStatuses = Arrays.asList(
						new UserStatus(1, "Active"),
						new UserStatus(2, "Inactive"),
						new UserStatus(3, "Deleted"));

				userStatusRepository.saveAll(userStatuses);
			}
		};
	}
}
