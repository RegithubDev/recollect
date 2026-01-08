package com.resustainability.recollect.tag;

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
	private final String description;

	KYCStatus(int ordinal, String description) {
		this.ordinal = ordinal;
        this.description = description;
    }

	public int getOrdinal() {
		return ordinal;
	}

	public String getDescription() {
		return description;
	}

	public static List<String> list() {
		return Stream.of(values())
				.map(KYCStatus::name)
				.toList();
	}

	@Override
	public String toString() {
		return name();
	}
}
