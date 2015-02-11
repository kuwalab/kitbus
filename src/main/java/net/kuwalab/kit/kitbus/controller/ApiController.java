package net.kuwalab.kit.kitbus.controller;

import java.io.IOException;
import java.util.Optional;

import net.kuwalab.kit.kitbus.model.ServiceTable;
import net.kuwalab.kit.kitbus.util.HttpUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {
	@RequestMapping(value = "/servicetable", method = RequestMethod.GET, produces = "application/json")
	public String servicetable() {
		Optional<String> stcsv = HttpUtil.getText(
				"http://www.kanazawa-it.ac.jp/shuttlebus/servicetable.csv",
				"Windows-31J");
		ServiceTable serviceTable = new ServiceTable(stcsv);

		ObjectMapper objectMapper = new ObjectMapper();
		Optional<String> jsonString = Optional.empty();

		try {
			jsonString = Optional.of(objectMapper
					.writeValueAsString(serviceTable));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString.orElse("{}");
	}

	@RequestMapping(value = "/timetable", method = RequestMethod.GET, produces = "application/json")
	public String timetable() {
		Optional<String> stcsv = HttpUtil.getText(
				"http://www.kanazawa-it.ac.jp/shuttlebus/timetable.csv",
				"Windows-31J");

		return stcsv.orElse("");
	}
}
