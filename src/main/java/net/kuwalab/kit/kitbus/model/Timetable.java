package net.kuwalab.kit.kitbus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author kuwalab
 */
public class Timetable {
	private ServiceDay serviceYoubi;

	private Shuttle shuttle;

	private List<String> busStopNameList;

	private List<List<String>> busStopTimeList;

	public Timetable() {
		busStopNameList = new ArrayList<>();
		busStopTimeList = new ArrayList<>();
	}

	public void readLine(String line) {
		// 曜日
		if (line.startsWith("月曜")) {
			serviceYoubi = ServiceDay.WEEKDAY;
			return;
		}
		if (line.startsWith("土曜(祝祭日")) {
			serviceYoubi = ServiceDay.SATURDAY;
			return;
		}
		// 往復
		if (line.startsWith("扇が丘→八束穂")) {
			shuttle = Shuttle.OUTWARD;
			return;
		}
		if (line.startsWith("八束穂→扇が丘")) {
			shuttle = Shuttle.HOMEWARD;
			return;
		}
		if (line.startsWith("便名")) {
			String[] busStopNames = line.split(",");
			for (int i = 1; i < busStopNames.length; i++) {
				busStopNameList.add(busStopNames[i]);
			}
			return;
		}
		// 残りは時刻表
		List<String> list = new ArrayList<>();
		busStopTimeList.add(list);
		String[] busStopTimes = line.split(",");
		for (int i = 1; i < busStopTimes.length; i++) {
			list.add(busStopTimes[i]);
		}
	}

	public ServiceDay getServiceYoubi() {
		return serviceYoubi;
	}

	public void setServiceYoubi(ServiceDay serviceYoubi) {
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
