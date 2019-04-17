package com.jtech.helper.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jtech.helper.logger.JtLogManager;
import com.jtech.helper.logger.JtLogger;

@Configuration
public class JtMapperConfigurer {

	private static final JtLogger logger = JtLogManager.getLogger(JtMapperConfigurer.class);

	@Bean
	public JtMapper jtMapper() {

		logger.info("Initializing the LtMapper");
		return new JtMapper();
	}
}