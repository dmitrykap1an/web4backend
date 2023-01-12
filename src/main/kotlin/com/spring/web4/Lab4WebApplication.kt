package com.spring.web4

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class WebApplication: SpringBootServletInitializer(){

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(WebApplication::class.java)
	}
}

fun main(args: Array<String>) {
	runApplication<WebApplication>(*args)
}
