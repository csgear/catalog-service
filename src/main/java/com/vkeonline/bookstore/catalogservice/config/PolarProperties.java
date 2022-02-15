package com.vkeonline.bookstore.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public record PolarProperties(
		String greeting
){}
