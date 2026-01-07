package com.resustainability.recollect.tag;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum KYCStatus {
	NOT_STARTED(0, "User onboarded"),
	MIN_KYC(1, "Mobile verified"),
	PAN_PENDING(2, "PAN submitted"),
	PAN_VERIFIED(3, "PAN verified"),
	BANK_VERIFIED(4, "Bank verified"),
	FULL_KYC(4, "Withdrawal allowed"),
	REJECTED(5, "KYC failed");

	private final int ordinal;
	private final String abbreviation;

	KYCStatus(int ordinal, String abbreviation) {
		this.ordinal = ordinal;
		this.abbreviation = abbreviation;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static KYCStatus fromAbbreviation(String abbreviation) {
		for (KYCStatus role : values()) {
			if (is(abbreviation, role)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Unknown abbreviation: " + abbreviation);
	}

	public static boolean is(String abbreviation, KYCStatus value) {
		return null != value && StringUtils.isNotBlank(abbreviation) && value
				.getAbbreviation()
				.equalsIgnoreCase(abbreviation.replace(Default.ROLE_PREFIX, Default.EMPTY));
	}

	public static boolean is(String abbreviation, KYCStatus... values) {
		return StringUtils.isNotBlank(abbreviation) && Arrays.stream(values)
				.anyMatch(value -> is(abbreviation, value));
	}

	public static List<String> list() {
		return Stream.of(values())
				.map(KYCStatus::getAbbreviation)
				.toList();
	}

	@Override
	public String toString() {
		return abbreviation;
	}
}
