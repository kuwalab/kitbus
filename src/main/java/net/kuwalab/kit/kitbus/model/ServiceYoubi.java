package net.kuwalab.kit.kitbus.model;

/**
 * 
 * @author kuwalab
 */
public enum ServiceYoubi {
	WEEKDAY, SATURDAY, SUNDAY;

	public static ServiceYoubi getServiceYoubi(String youbi) {
		if (youbi == null) {
			return WEEKDAY;
		}
		switch (youbi) {
		case "SATURDAY":
			return SATURDAY;
		case "SUNDAY":
			return SUNDAY;
		default:
			return WEEKDAY;
		}
	}
}
