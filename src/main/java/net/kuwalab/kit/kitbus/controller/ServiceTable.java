package net.kuwalab.kit.kitbus.controller;

import java.util.Map;
import java.util.Optional;

public class ServiceTable {
	private int version;
	private Map<String, ServiceYoubi> serviceMap;

	private static final int VERSION_NOTHING = -1;

	public ServiceTable(Optional<String> stcsv) {
		if (!searchVersion(stcsv)) {
			version = VERSION_NOTHING;
			return;
		}
	}

	private boolean searchVersion(Optional<String> stcsv) {
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

		return true;
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
}
