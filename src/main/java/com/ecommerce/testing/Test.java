package com.ecommerce.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/hello")
public class Test {

	@GetMapping("/world")
public String hello() {
	return "hello";
	}
}
