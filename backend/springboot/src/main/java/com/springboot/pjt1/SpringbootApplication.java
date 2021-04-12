package com.springboot.pjt1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.springboot.pjt1.repository.dto.ImageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	ImageProperties.class
})
@MapperScan(basePackages = "com.springboot.pjt1.repository.mapper")
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
