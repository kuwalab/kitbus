package net.kuwalab.kit.kitbus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
	@RequestMapping("/")
	public String home() {
		return "Hello world";
	}
}
