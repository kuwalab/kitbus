package net.kuwalab.kit.kitbus.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import net.kuwalab.kit.kitbus.model.ServiceTable;
import net.kuwalab.kit.kitbus.model.TimeTables;
import net.kuwalab.kit.kitbus.util.HttpUtil;
import net.kuwalab.kit.kitbus.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory
			.getLogger(ApiController.class);

	private LocalDateTime serviceTableTime;
	private ServiceTable serviceTable;

	private LocalDateTime timeTablesTime;
	private TimeTables timeTables;

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

		return JsonUtil.objectToJson(serviceTable);
	}

	@RequestMapping(value = "/timetable", method = RequestMethod.GET, produces = "application/json")
	public String timetable() {
		if (passOneDay(timeTablesTime)) {
			logger.info("timeTables create");
			Optional<String> ttcsv = HttpUtil.getText(
					"http://www.kanazawa-it.ac.jp/shuttlebus/timetable.csv",
					"Windows-31J");

			timeTables = new TimeTables(ttcsv);
		}

		return JsonUtil.objectToJson(timeTables);
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
