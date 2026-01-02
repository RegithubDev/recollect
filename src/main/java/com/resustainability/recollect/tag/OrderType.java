package com.resustainability.recollect.tag;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum OrderType {
	SCRAP(0, "scrap"),
	BIO_WASTE(1, "biowaste"),
	BWG(2, "bwg");

	private final int ordinal;
	private final String abbreviation;

	OrderType(int ordinal, String abbreviation) {
		this.ordinal = ordinal;
		this.abbreviation = abbreviation;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static OrderType fromAbbreviation(String abbreviation) {
		for (OrderType role : values()) {
			if (is(abbreviation, role)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Unknown abbreviation: " + abbreviation);
	}

	public static boolean is(String abbreviation, OrderType value) {
		return null != value && StringUtils.isNotBlank(abbreviation) && value
				.getAbbreviation()
				.equalsIgnoreCase(abbreviation.replace(Default.ROLE_PREFIX, Default.EMPTY));
	}

	public static boolean is(String abbreviation, OrderType... values) {
		return StringUtils.isNotBlank(abbreviation) && Arrays.stream(values)
				.anyMatch(value -> is(abbreviation, value));
	}

	public static List<String> list() {
		return Stream.of(values())
				.map(OrderType::getAbbreviation)
				.toList();
	}

	@Override
	public String toString() {
		return abbreviation;
	}
}
