package net.kuwalab.kit.kitbus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author kuwalab
 */
public class Timetables {
	private int version;

	private List<Timetable> timetableList;

	private static final int VERSION_NOTHING = -1;

	public Timetables(Optional<String> ttcsv) {
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
		timetableList = new ArrayList<>();
		Timetable timetable = new Timetable();
		ServiceDay beforeServiceDay = null;
		for (int i = 1; i < lines.length; i++) {
			if (lines[i].startsWith("end")) {
				// 運行曜日は連続している場合省略されるので、保管しておく
				if (timetable.getServiceDay() != null) {
					beforeServiceDay = timetable.getServiceDay();
				}
				timetableList.add(timetable);
				if (i == lines.length - 1) {
					break;
				}
				timetable = new Timetable();
				// 保管していた運用曜日をセットする
				// もし、運行曜日があれば上書きされるので問題ない
				timetable.setServiceDay(beforeServiceDay);
				continue;
			}
			timetable.readLine(lines[i]);
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<Timetable> getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}
}
