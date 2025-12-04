package com.resustainability.recollect.tag;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum OrderStatus {
	PENDING(0, "pending"),
	OPEN(1, "open"),
	CONFIRMED(2, "confirmed"),
	COMPLETED(3, "completed"),
	REACHED_LOCATION(4, "reached location"),
	CANCELLED(5, "cancelled");

	private final int ordinal;
	private final String abbreviation;

	OrderStatus(int ordinal, String abbreviation) {
		this.ordinal = ordinal;
		this.abbreviation = abbreviation;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static OrderStatus fromAbbreviation(String abbreviation) {
		for (OrderStatus role : values()) {
			if (is(abbreviation, role)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Unknown abbreviation: " + abbreviation);
	}

	public static boolean is(String abbreviation, OrderStatus value) {
		return null != value && StringUtils.isNotBlank(abbreviation) && value
				.getAbbreviation()
				.equalsIgnoreCase(abbreviation.replace(Default.ROLE_PREFIX, Default.EMPTY));
	}

	public static boolean is(String abbreviation, OrderStatus... values) {
		return StringUtils.isNotBlank(abbreviation) && Arrays.stream(values)
				.anyMatch(value -> is(abbreviation, value));
	}

	public static List<String> list() {
		return Stream.of(values())
				.map(OrderStatus::getAbbreviation)
				.toList();
	}

	@Override
	public String toString() {
		return abbreviation;
	}
}
