package net.kuwalab.kit.kitbus.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author kuwalab
 */
public class ServiceTable {
	private int version;
	private Map<String, ServiceYoubi> serviceMap;

	private static final int VERSION_NOTHING = -1;

	public ServiceTable(Optional<String> stcsv) {
		if (!readCsv(stcsv)) {
			version = VERSION_NOTHING;
		}
	}

	private boolean readCsv(Optional<String> stcsv) {
		if (!stcsv.isPresent()) {
			return false;
		}

		String csv = stcsv.get();
		String[] lines = csv.split("\n");
		if (lines.length == 0) {
			return false;
		}

		String[] versionString = lines[0].split(",");
		if (versionString.length != 2) {
			return false;
		}
		try {
			version = Integer.parseInt(versionString[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		readServiceTable(lines);

		return true;
	}

	private void readServiceTable(String[] lines) {
		serviceMap = new HashMap<String, ServiceYoubi>();

		// 長さが2以上であることはチェック済み
		for (int i = 2; i < lines.length; i++) {
			String[] dates = lines[i].split(",");
			if (dates.length != 2) {
				// ないはず
				continue;
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d");
			LocalDate dateTime;
			try {
				dateTime = LocalDate.parse(dates[0], dtf);
			} catch (DateTimeParseException e) {
				// ないはず
				continue;
			}
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String date = dateTime.format(dtf);
			serviceMap.put(date, ServiceYoubi.getServiceYoubi(dates[1]));
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Map<String, ServiceYoubi> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, ServiceYoubi> serviceMap) {
		this.serviceMap = serviceMap;
	}

	@Override
	public String toString() {
		return "ServiceTable [version=" + version + ", serviceMap="
				+ serviceMap + "]";
	}
}
