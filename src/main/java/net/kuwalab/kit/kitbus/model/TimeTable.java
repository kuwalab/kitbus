package net.kuwalab.kit.kitbus.model;

public class TimeTable {
	private ServiceYoubi serviceYoubi;

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
	}

	public ServiceYoubi getServiceYoubi() {
		return serviceYoubi;
	}

	public void setServiceYoubi(ServiceYoubi serviceYoubi) {
		this.serviceYoubi = serviceYoubi;
	}
}
