package net.kuwalab.kit.kitbus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeTables {
	private static final Logger logger = LoggerFactory
			.getLogger(TimeTables.class);

	private int version;

	private List<TimeTable> timeTableList;

	private static final int VERSION_NOTHING = -1;

	public TimeTables(Optional<String> ttcsv) {
		if (!readCsv(ttcsv)) {
			version = VERSION_NOTHING;
		}
	}

	private boolean readCsv(Optional<String> ttcsv) {
		if (!ttcsv.isPresent()) {
			return false;
		}

		String csv = ttcsv.get();
		String[] lines = csv.split("\n");
		if (lines.length == 0) {
			return false;
		}

		String[] versionString = lines[0].split(",");
		if (versionString.length < 2) {
			return false;
		}
		try {
			version = Integer.parseInt(versionString[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		readTimeTable(lines);

		return true;
	}

	private void readTimeTable(String[] lines) {
		// 長さが1以上であることは確認済み
		timeTableList = new ArrayList<>();
		TimeTable timeTable = new TimeTable();
		ServiceYoubi beforeServiceYoubi = null;
		for (int i = 1; i < lines.length; i++) {
			if (lines[i].startsWith("end")) {
				// 運行曜日は連続している場合省略されるので、保管しておく
				if (timeTable.getServiceYoubi() != null) {
					beforeServiceYoubi = timeTable.getServiceYoubi();
				}
				timeTableList.add(timeTable);
				if (i == lines.length - 1) {
					break;
				}
				timeTable = new TimeTable();
				// 保管していた運用曜日をセットする
				// もし、運行曜日があれば上書きされるので問題ない
				timeTable.setServiceYoubi(beforeServiceYoubi);
				continue;
			}
			timeTable.readLine(lines[i]);
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<TimeTable> getTimeTableList() {
		return timeTableList;
	}

	public void setTimeTableList(List<TimeTable> timeTableList) {
		this.timeTableList = timeTableList;
	}
}
