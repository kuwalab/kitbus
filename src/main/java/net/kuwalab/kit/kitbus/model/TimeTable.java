package net.kuwalab.kit.kitbus.model;

import java.util.ArrayList;
import java.util.List;

public class TimeTable {
	private ServiceYoubi serviceYoubi;

	private Shuttle shuttle;

	private List<String> busStopNameList;

	private List<List<String>> busStopTimeList;

	public void readLine(String line) {
		// 曜日
		if (line.startsWith("月曜")) {
			serviceYoubi = ServiceYoubi.WEEKDAY;
			return;
		}
		if (line.startsWith("土曜(祝祭日")) {
			serviceYoubi = ServiceYoubi.SATURDAY;
			return;
		}
		// 往復
		if (line.startsWith("扇が丘→八束穂")) {
			shuttle = Shuttle.Outward;
			return;
		}
		if (line.startsWith("八束穂→扇が丘")) {
			shuttle = Shuttle.Homeward;
			return;
		}
		if (line.startsWith("便名")) {
			String[] busStopNames = line.split(",");
			busStopNameList = new ArrayList<>();
			for (int i = 1; i < busStopNames.length; i++) {
				busStopNameList.add(busStopNames[i]);
			}
			return;
		}
	}

	public ServiceYoubi getServiceYoubi() {
		return serviceYoubi;
	}

	public void setServiceYoubi(ServiceYoubi serviceYoubi) {
		this.serviceYoubi = serviceYoubi;
	}

	public Shuttle getShuttle() {
		return shuttle;
	}

	public void setShuttle(Shuttle shuttle) {
		this.shuttle = shuttle;
	}

	public List<String> getBusStopNameList() {
		return busStopNameList;
	}

	public void setBusStopNameList(List<String> busStopNameList) {
		this.busStopNameList = busStopNameList;
	}

	public List<List<String>> getBusStopTimeList() {
		return busStopTimeList;
	}

	public void setBusStopTimeList(List<List<String>> busStopTimeList) {
		this.busStopTimeList = busStopTimeList;
	}
}
