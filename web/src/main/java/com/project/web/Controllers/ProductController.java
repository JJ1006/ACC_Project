package com.project.web.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@GetMapping("/home")
	public String getController() {
		return "Hello world";
	}
}
