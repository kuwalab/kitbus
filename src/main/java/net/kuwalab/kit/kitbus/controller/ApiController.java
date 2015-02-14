package net.kuwalab.kit.kitbus.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import net.kuwalab.kit.kitbus.model.ServiceTable;
import net.kuwalab.kit.kitbus.model.TimeTables;
import net.kuwalab.kit.kitbus.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory
			.getLogger(ApiController.class);

	private LocalDateTime serviceTableTime;
	private ServiceTable serviceTable;

	@RequestMapping(value = "/servicetable", method = RequestMethod.GET, produces = "application/json")
	public String servicetable() {
		if (passOneDay(serviceTableTime)) {
			logger.info("serviceTable create");
			serviceTableTime = LocalDateTime.now();
			Optional<String> stcsv = HttpUtil.getText(
					"http://www.kanazawa-it.ac.jp/shuttlebus/servicetable.csv",
					"Windows-31J");
			serviceTable = new ServiceTable(stcsv);
		}

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
		Optional<String> ttcsv = HttpUtil.getText(
				"http://www.kanazawa-it.ac.jp/shuttlebus/timetable.csv",
				"Windows-31J");

		TimeTables timeTables = new TimeTables(ttcsv);

		ObjectMapper objectMapper = new ObjectMapper();
		Optional<String> jsonString = Optional.empty();

		try {
			jsonString = Optional.of(objectMapper
					.writeValueAsString(timeTables));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString.orElse("{}");
	}

	/**
	 * 1日経過したかをチェックする
	 * 
	 * @param localDateTime
	 * @return
	 */
	private boolean passOneDay(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return true;
		}
		LocalDateTime passOneDayTime = localDateTime.plusDays(1);
		return LocalDateTime.now().isAfter(passOneDayTime);
	}
}
