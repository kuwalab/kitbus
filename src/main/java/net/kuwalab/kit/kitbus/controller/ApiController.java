package net.kuwalab.kit.kitbus.controller;

import java.util.Optional;

import net.kuwalab.kit.kitbus.util.HttpUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	@RequestMapping("/servicetable")
	public String servicetable() {
		Optional<String> stcsv = HttpUtil.getText(
				"http://www.kanazawa-it.ac.jp/shuttlebus/servicetable.csv",
				"Windows-31J");
		String csv = stcsv.orElse("");

		return "Hello world!";
	}
}
