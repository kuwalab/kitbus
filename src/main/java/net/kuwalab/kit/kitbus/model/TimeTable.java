package net.kuwalab.kit.kitbus.model;

public class TimeTable {
	private ServiceYoubi serviceYoubi;

	private Shuttle shuttle;

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
}
