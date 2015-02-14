package net.kuwalab.kit.kitbus.model;

/**
 * 
 * @author kuwalab
 */
public enum ServiceDay {
	WEEKDAY, SATURDAY, SUNDAY;

	public static ServiceDay getServiceDay(String youbi) {
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
